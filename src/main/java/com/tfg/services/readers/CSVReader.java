package com.tfg.services.readers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tfg.model.AssociationAsset;
import com.tfg.model.DigitalAsset;
import com.tfg.model.Field;
import com.tfg.model.Group;
import com.tfg.model.GroupField;
import com.tfg.model.Value;
import com.tfg.services.AssociationAssetService;
import com.tfg.services.DigitalAssetService;
import com.tfg.services.FieldService;
import com.tfg.services.GroupFieldService;
import com.tfg.services.GroupService;
import com.tfg.services.ValueService;
import com.tfg.services.adapters.DigitalAssetAdapter;

/**
 * Servicio encargado de obtener metadatos específicos de los archivos CSV
 * @author gcollada
 *
 */
@Service
@Transactional
public class CSVReader {
	
	/**
	 * Servicio para realizar operaciones lógicas sobre grupos
	 */
	@Autowired
	GroupService groupService;
	
	/**
	 * Servicio para realizar operaciones lógicas sobre campos
	 */
	@Autowired
	FieldService fieldService;
	
	/**
	 * Servicio para realizar operaciones lógicas sobre valores
	 */
	@Autowired
	ValueService valueService;
	
	/**
	 * Servicio para realizar operaciones lógicas sobre GroupFields (agrupaciones de metadatos)
	 */
	@Autowired
	GroupFieldService groupFieldService;
	
	/**
	 * Servicio para realizar operaciones lógicas sobre AssociationAssets
	 */
	@Autowired
	AssociationAssetService ac_assetService;
	
	/**
	 * Servicio para realizar operaciones lógicas sobre DigitalAssets
	 */
	@Autowired
	DigitalAssetService assetService;
	
	/**
	 * Clase interna de CSVReader que representa una columna del CSV
	 * @author gcollada
	 *
	 */
	private class Key{
		String name;
		Type type;	
	}
	
	private static final String SEPARATOR = ";";
	private static final String defaultPath = "src/main/resources/";
	/**
	 * Tipo que puede tener una columna del CSV
	 * @author gcollada
	 *
	 */
	private enum Type{
		TEXT, NUMBER, DATE, UNKNOWN
	}
	private static Pattern ALPHANUMERIC_PATTERN = Pattern.compile(
			"^(([a-zA-Z]+\\d*)||(\\d+[a-zA-Z]+))*$");
	private static Pattern NUMBER_PATTERN = Pattern.compile(
			"^[-+]?\\d+(\\,\\d+)?$");
	private static Pattern DATE_PATTERN = Pattern.compile(
		      "^\\d{2}\\/\\d{2}\\/\\d{4}$");
	int nCol = 0;
	int nRow = 0;
	Map<Key,ArrayList<String>> mapa = new HashMap<>();
	
	/**
	 * Método encargado de leer el archivo CSV fila por fila.
	 * Obtiene el número de filas y columna e invoca a la obtención de metadatos
	 * @param csvFile Nombre del archivo CSV a procesar
	 */
	public void read(String csvFile) {
		BufferedReader br = null;
		String line = "";
		ArrayList<Key> header= new ArrayList<>();
		try {
			br = new BufferedReader(new FileReader(defaultPath+csvFile));
			while((line = br.readLine()) !=null) {
				if(nRow == 0) {
					String[] names = line.split(SEPARATOR);
					for(int i=0; i<names.length;i++) {
						header.add(new Key());
						header.get(i).name=names[i];
					}
					nRow++;
				}else {
					String[] datos = line.split(SEPARATOR);
					for(int i=0; i<datos.length;i++) {
						ArrayList<String> arrVal = mapa.get(header.get(i));
						if (arrVal == null) {
							arrVal = new ArrayList<String>();
							mapa.put(header.get(i),arrVal);
						}
						arrVal.add(datos[i]);
					}
					nRow++; nCol = datos.length;
				}
			}
			scan(mapa,csvFile);
			br.close();
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Método encargado de procesar el archivo CSV y obtener los metadatos relacionados con el tipo y valores
	 * de las columnas del CSV, los nombres de las columnas, además de llamar a los correspondientes servicio
	 * para añadirlos a la base de datos
	 * @param map Mapa con la información tabulada del CSV
	 * @param csvFile Nombre del fichero CSV
	 */
	private void scan(Map<Key,ArrayList<String>> map, String csvFile) {
		DigitalAsset asset = DigitalAssetAdapter.getAssetEntity(assetService.findByCodigo(csvFile));
		List<Group> listGroups = new ArrayList<Group>();
		Group group = new Group("CSV_basics", "Basic metadata associated to CSV files");
		listGroups.add(group);
		groupService.addListGroups(listGroups);
		List<String> listFields = new ArrayList<String>();
		listFields.addAll(map.keySet().stream().map(entry -> "csv_column_"+entry.name).collect(Collectors.toList()));
		listFields.addAll(map.keySet().stream().map(entry -> "csv_column_"+entry.name+"_type").collect(Collectors.toList()));
		listFields.add("nColumns");	listFields.add("nRows");
		fieldService.addListFieldsCodes(listFields);
		List<String> listValues = new ArrayList<String>();
		listValues.add(String.valueOf(nCol)); listValues.add(String.valueOf(nRow));
		List<GroupField> listGroupFields = new ArrayList<GroupField>();
		List<AssociationAsset> asociaciones = new ArrayList<AssociationAsset>();
		group = groupService.getGroupByCode("CSV_basics");
		for(Key key: map.keySet()) {
			listValues.add(map.get(key).stream().map(Object::toString).collect(Collectors.joining(";")));		
			Type t = key.type=Type.valueOf(getType(map.get(key).get(0)));
			listValues.add(t.name());
		}	
		valueService.addListValores(listValues);
		GroupField gf = new GroupField(group,fieldService.getFieldByCode("nColumns"),
				valueService.getValor(String.valueOf(nCol)));
		GroupField gf2 = new GroupField(group,fieldService.getFieldByCode("nRows"),
				valueService.getValor(String.valueOf(nRow)));
		listGroupFields.add(gf);listGroupFields.add(gf2);
		asociaciones.add(new AssociationAsset(asset,gf));
		asociaciones.add(new AssociationAsset(asset,gf2));
		for(Key key: map.keySet()) {
			Field field = fieldService.getFieldByCode("csv_column_"+key.name);
			Value value = valueService.getValor(map.get(key).stream().map(Object::toString).collect(Collectors.joining(";")));
			Field field2 = fieldService.getFieldByCode("csv_column_"+key.name+"_type");
			Value value2 = valueService.getValor(key.type.name());
			gf = new GroupField(group,field,value);
			gf2 = new GroupField(group,field2,value2);
			listGroupFields.add(gf);listGroupFields.add(gf2);
			asociaciones.add(new AssociationAsset(asset,gf));
			asociaciones.add(new AssociationAsset(asset,gf2));
		}
		groupFieldService.addListGroupFields(listGroupFields);
		ac_assetService.addListAssociationsAsset(asociaciones);
		asset.getAsociaciones_asset().addAll(asociaciones);
	}
	
	/**
	 * Obtiene el tipo de una columna del CSV
	 * @param value Valor a comparar con los tipos
	 * @return Cadena de texto con el tipo del parámetro
	 */
	private String getType(String value) {
		boolean matcher = ALPHANUMERIC_PATTERN.matcher(value).matches();
		if (matcher) {
			return "TEXT";
		} else if(matcher = NUMBER_PATTERN.matcher(value).matches()) {
			return "NUMBER";
		} else if (matcher = DATE_PATTERN.matcher(value).matches()) {
			return "DATE";
		} else {
			return "UNKNOWN";
		}
	}
	
	public Map<String,String> getSimpleContent(String csvFile) {
		BufferedReader br = null;
		String line = "";
		Map<String,String> mapa = new HashMap<>();
		try {
			br = new BufferedReader(new FileReader(csvFile));
			while((line = br.readLine()) !=null) {
				String[] datos = line.split(SEPARATOR);
				mapa.put(datos[0], datos[1]);
			}
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		return mapa;
	}

}
