package com.tfg.services.azure;

import java.io.FileOutputStream;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.microsoft.azure.storage.*;
import com.microsoft.azure.storage.blob.*;
import com.tfg.exceptions.errors.RestException;
import com.tfg.services.model.NewAsset;
import com.tfg.services.readers.FileInfo;
import com.tfg.utils.ContextManager;

/**
 * Clase que realiza las operaciones relacionadas con el servicio Storage Service de Azure.
 * @author gcollada
 *
 */
@Service
public class AzureStorageService {
	
	/**
	 * Servicio encargado de obtener metadatos automáticamente.
	 */
	@Autowired
	FileInfo fileService;
	
	/**
	 * Instancia de ContextManager
	 */
	static ContextManager cm = ContextManager.getInstance();
	
	/**
	 * Cadena de conexión al servicio Azure Storage Service
	 */
	public static final String storageConnectionString =
	    "DefaultEndpointsProtocol=https;" +
	    "AccountName="+cm.getProperty("data_storage_name")+";" +
	    "AccountKey="+cm.getProperty("data_storage_key");
	
	private static final String defaultPath = "src/main/resources/";
	
	/**
	 * Método que descarga los archivos contenidos en el repositorio Azure Storage al workspace del proyecto
	 * para posteriormente obtener los metadatos y borrarlo
	 * @param session
	 * @param containerName Nombre de la fuente de datos
	 * @param fileName Nombre del archivo, puede ser una expresión regular
	 */
	@SuppressWarnings("serial")
	public void downloadFile(HttpSession session, String containerName, String fileName) {
		try
		{
		   CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);
		   // Create the blob client.
		   CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
		   // Retrieve reference to a previously created container.
		   CloudBlobContainer container = blobClient.getContainerReference(containerName);
		   if(fileName.equals("*")) {
			   for (ListBlobItem blobItem : container.listBlobs()) {
			       // If the item is a blob, not a virtual directory.
			       if (blobItem instanceof CloudBlob) {
			           // Download the item and save it to a file with the same name.
				        CloudBlob blob = (CloudBlob) blobItem;
				        blob.download(new FileOutputStream(defaultPath + blob.getName()));
						fileService.getMetadata(session, blob.getName(),new NewAsset());
				    }
				} 
		   }else {
			   for (ListBlobItem blobItem : container.listBlobs(fileName)) {
			       // If the item is a blob, not a virtual directory.
			       if (blobItem instanceof CloudBlob) {
			           // Download the item and save it to a file with the same name.
				        CloudBlob blob = (CloudBlob) blobItem;
				        blob.download(new FileOutputStream(defaultPath + blob.getName()));
						fileService.getMetadata(session, blob.getName(),new NewAsset());
				    }
				} 
		   }
		   
		}
		catch (Exception e)
		{
		    throw new RestException(e,HttpStatus.INTERNAL_SERVER_ERROR) {
			};
		}
	}

}
