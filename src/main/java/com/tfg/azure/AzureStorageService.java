package com.tfg.azure;

import java.io.FileOutputStream;

import org.springframework.stereotype.Service;

import com.microsoft.azure.storage.*;
import com.microsoft.azure.storage.blob.*;

/**
 * 
 * @author gcollada
 *
 */
@Service
public class AzureStorageService {
	
	// Define the connection-string with your values
	public static final String storageConnectionString =
	    "DefaultEndpointsProtocol=https;" +
	    "AccountName=datastoragegcv;" +
	    "AccountKey=L4VdplG0UyFmtk2x9CsWzc5OGvuuO3JRoqFE3TzKOREoYigRI1dBJResC8RQcSBKrPP+xNjqS7bHWqrg6nJUhg==";
	
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
