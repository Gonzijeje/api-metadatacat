package com.tfg.services.readers;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.services.DigitalAssetService;
import com.tfg.services.model.NewAsset;

/**
 * Serviciio encargado de obtene rmetadatos automáticamente de cualquier archivo procesado
 * durante las canalizaciones de Azure. Obtiene los metadatos, se almacenan en el sistema y se invoca a servicios mas especificos
 * de obtencion dependiendo de la extension del archivo
 * @author gcollada
 *
 */
@Service
public class FileInfo {
	
	/**
	 * Servicio para realizar operaciones lógicas sobre DigitalAssets
	 */
	@Autowired
	DigitalAssetService assetService;
	
	/**
	 * Servicio dedicado a obtener metadatos de los CSV
	 */
	@Autowired
	CSVReader csvService;
	
	/**
	 * Servicio dedicado a obtener metadatos de los TXT
	 */
	@Autowired
	TXTReader txtService;
	
	/**
	 * Enum con los distintos tipos de acceso a un archivo
	 * @author yeahb
	 *
	 */
	public enum Timefield {
		CREATED, ACCESSED, WRITTEN
	}
	
	
	private static final String defaultPath = "src/main/resources/";	
	private final static DateFormat FORMATTER = new SimpleDateFormat(
			"dd/MM/yyyy  hh:mm");
	
	private static Pattern CSV_PATTERN = Pattern.compile(
			"^\\w*\\.csv$");
	private static Pattern TXT_PATTERN = Pattern.compile(
			"^\\w*\\.txt$");
	
	private File file;
	private boolean hasLoaded = false;
	private String owner;
	private Map<Timefield, Date> timefields = new HashMap<Timefield, Date>();
	
	private String getTimefieldSwitch(Timefield field) {
		switch (field) {
		case CREATED:
			return "C";
		case ACCESSED:
			return "A";
		default:
			return "W";
		}
	}
	
	private void shellToDir(Timefield timefield) throws IOException,
			ParseException {
		Runtime systemShell = Runtime.getRuntime();
		Process output = systemShell.exec(String.format("cmd /c dir /Q /R /T%s %s ", getTimefieldSwitch(timefield), file.getAbsolutePath()));
		BufferedReader reader = new BufferedReader(new InputStreamReader(output.getInputStream()));
		String outputLine = null;
		while ((outputLine = reader.readLine()) != null) {
			if (outputLine.contains(file.getName())) {
				System.out.println(outputLine);
				timefields.put(timefield,
						FORMATTER.parse(outputLine.substring(0, 17)));
				owner = outputLine.substring(36, 59);
			}
		}
	}
	
	private void load() throws IOException, ParseException {
		if (hasLoaded)
			return;
		shellToDir(Timefield.CREATED);
		shellToDir(Timefield.ACCESSED);
		shellToDir(Timefield.WRITTEN);
	}
	
	public String getName() {
		return file.getName();
	}
	
	public String getAbsolutePath() {
		return file.getAbsolutePath();
	}
	
	public long getSize() {
		return file.length();
	}
	
	public Date getLastModified() {
		return new Date(file.lastModified());
	}
	
	public String getOwner() throws IOException, ParseException {
		load();
		return owner;
	}
	
	public Date getCreated() throws IOException, ParseException {
		load();
		return timefields.get(Timefield.CREATED);
	}
	
	public Date getAccessed() throws IOException, ParseException {
		load();
		return timefields.get(Timefield.ACCESSED);
	}
	
	public Date getWritten() throws IOException, ParseException {
		load();
		return timefields.get(Timefield.WRITTEN);
	}
	
	/**
	 * Método que se encarga de invocar al servicio de DigitalAssets para añadir un nuevo archivo con los metadatos obtenidos
	 * durante el procesado llevado a cabo en esta clase
	 * @param session
	 * @param fileName
	 * @param newAsset
	 * @throws IOException
	 * @throws ParseException
	 */
	public void getMetadata(HttpSession session, String fileName, NewAsset newAsset) throws IOException, ParseException {
		this.file = new File(defaultPath+fileName);
		System.out.println("Antes de añadir");
		assetService.addRealAsset(fileName, getNewAsset(fileName));
		getFileType(session, fileName);
	}
	
	private NewAsset getNewAsset(String fileName) throws IOException, ParseException {
		NewAsset newAsset = new NewAsset();
		newAsset.setCode(fileName);
		newAsset.setName(fileName);
		newAsset.setCreateDate(getCreated());
		newAsset.setDescription("Digital Asset with name "+fileName);
		newAsset.setLastModifiedDate(getLastModified());
		newAsset.setOwner(getOwner());
		newAsset.setPath(getAbsolutePath());
		newAsset.setSize((int)getSize());
		System.out.println("Antes de añadir 2");
		return newAsset;
	}
	
	private void getFileType(HttpSession session, String fileName) {
		if(CSV_PATTERN.matcher(fileName).matches()) {
			csvService.read(fileName);
		}else if(TXT_PATTERN.matcher(fileName).matches()) {
			txtService.read(session, fileName);
		}
	}
}
