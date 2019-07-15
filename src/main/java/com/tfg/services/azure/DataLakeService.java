package com.tfg.services.azure;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;

import javax.servlet.http.HttpSession;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.exceptions.ExceptionFactory;
import com.tfg.exceptions.ExceptionFactory.Errors;
import com.tfg.services.model.NewAsset;
import com.tfg.services.readers.FileInfo;
import com.tfg.utils.ContextManager;

/**
 * Clase que contiene las operaciones relacionadas con el servicio Data Lake de Azure
 * @author gcollada
 *
 */
@Service
public class DataLakeService {
	
	/**
	 * Instancia de la clase ContextManager
	 */
	ContextManager cm = ContextManager.getInstance();
	/**
	 * Ruta donde se descargarán los archivos del Data Lake
	 */
	private static final String defaultPath = "src/main/resources/";
	
	/**
	 * Servicio encargado de obtener metadatos automáticamente
	 */
	@Autowired
	FileInfo fileService;
	
	/**
	 * Método que descarga archivos contenidos en el Data Lake de destino integrado en el sistema para obtener metadatos
	 * y almacenarlos en la base de datos del proyecto junto a al referencia del propio archivo
	 * @param session
	 * @param pathFile Ruta del archivo a escargar
	 */
	public void download(HttpSession session, String pathFile){
		String path = defaultPath+pathFile;
		File myFile = new File(path);
		HttpClient client = HttpClients.createDefault();
		String URL = "https://"+cm.getProperty("data_lake_name")+".azuredatalakestore.net/webhdfs/v1/"+pathFile+"?op=OPEN&read=true";
		HttpGet request = new HttpGet(URL);
		request.setHeader("Authorization", "Bearer "+session.getAttribute("bearer_token"));
		try {
			HttpResponse response = client.execute(request);
			HttpEntity entity = response.getEntity();
			if(entity!=null) {
				FileOutputStream outstream = new FileOutputStream(myFile);
				entity.writeTo(outstream);
				fileService.getMetadata(session, pathFile,new NewAsset());
			}
		} catch (IOException | ParseException e) {
			throw  ExceptionFactory.getError(Errors.HTTP_CLIENT_ERROR);
		}		
	}

}
