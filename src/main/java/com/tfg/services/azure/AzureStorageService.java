package com.tfg.services.azure;

import java.io.FileOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microsoft.azure.storage.*;
import com.microsoft.azure.storage.blob.*;
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
	
	public void downloadFile(String containerName, String fileName) {
		try
		{
		   CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);
		   // Create the blob client.
		   CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
		   // Retrieve reference to a previously created container.
		   CloudBlobContainer container = blobClient.getContainerReference(containerName);		   
		   CloudBlockBlob blob = container.getBlockBlobReference(fileName);
		   blob.download(new FileOutputStream(defaultPath + blob.getName()));
		   fileService.getMetadata(fileName,new NewAsset());
		}
		catch (Exception e)
		{
		    e.printStackTrace();
		}
	}

}
