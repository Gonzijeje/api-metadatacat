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
	
	public void download() {
		try
		{
			// Retrieve storage account from connection-string.
		   CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);

		   // Create the blob client.
		   CloudBlobClient blobClient = storageAccount.createCloudBlobClient();

		   // Retrieve reference to a previously created container.
		   CloudBlobContainer container = blobClient.getContainerReference("datainput1");

		   // Loop through each blob item in the container.
		   for (ListBlobItem blobItem : container.listBlobs()) {
		       // If the item is a blob, not a virtual directory.
		       if (blobItem instanceof CloudBlob) {
		           // Download the item and save it to a file with the same name.
			        CloudBlob blob = (CloudBlob) blobItem;
			        blob.download(new FileOutputStream("src/main/resources/" + blob.getName()));
			    }
			}
		}
		catch (Exception e)
		{
		    // Output the stack trace.
		    e.printStackTrace();
		}
	}

}
