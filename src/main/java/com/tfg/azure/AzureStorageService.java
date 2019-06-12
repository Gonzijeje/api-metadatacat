package com.tfg.azure;

import java.io.FileOutputStream;

import org.springframework.stereotype.Service;

import com.microsoft.azure.storage.*;
import com.microsoft.azure.storage.blob.*;
import com.tfg.ContextManager;

/**
 * 
 * @author gcollada
 *
 */
@Service
public class AzureStorageService {
	
	static ContextManager cm = ContextManager.getInstance();
	
	// Define the connection-string with your values
	public static final String storageConnectionString =
	    "DefaultEndpointsProtocol=https;" +
	    "AccountName="+cm.getProperty("data_storage_name")+";" +
	    "AccountKey="+cm.getProperty("data_storage_key");
	
	public void downloadFile(String containerName, String fileName) {
		try
		{
			// Retrieve storage account from connection-string.
		   CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);
		   // Create the blob client.
		   CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
		   // Retrieve reference to a previously created container.
		   CloudBlobContainer container = blobClient.getContainerReference(containerName);		   
		   CloudBlockBlob blob = container.getBlockBlobReference(fileName);
		   blob.download(new FileOutputStream("src/main/resources/" + blob.getName()));
		}
		catch (Exception e)
		{
		    e.printStackTrace();
		}
	}

}
