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
 * 
 * @author gcollada
 *
 */
@Service
public class AzureStorageService {
	
	@Autowired
	FileInfo fileService;
	
	static ContextManager cm = ContextManager.getInstance();
	
	// Define the connection-string with your values
	public static final String storageConnectionString =
	    "DefaultEndpointsProtocol=https;" +
	    "AccountName="+cm.getProperty("data_storage_name")+";" +
	    "AccountKey="+cm.getProperty("data_storage_key");
	
	private static final String defaultPath = "src/main/resources/";
	
	@SuppressWarnings("serial")
	public void downloadFile(HttpSession session, String containerName, String fileName) {
		try
		{
		   CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);
		   // Create the blob client.
		   CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
		   // Retrieve reference to a previously created container.
		   CloudBlobContainer container = blobClient.getContainerReference(containerName);
		   for (ListBlobItem blobItem : container.listBlobs(fileName)) {
			   System.out.println();
		       // If the item is a blob, not a virtual directory.
		       if (blobItem instanceof CloudBlob) {
		           // Download the item and save it to a file with the same name.
			        CloudBlob blob = (CloudBlob) blobItem;
			        blob.download(new FileOutputStream(defaultPath + blob.getName()));
			        System.out.println("ANtes de obtener metadatos");
					fileService.getMetadata(session, fileName,new NewAsset());
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
