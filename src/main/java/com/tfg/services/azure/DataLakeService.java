package com.tfg.services.azure;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

import com.tfg.controller.azure.DirectoryEntry;
import com.tfg.controller.azure.List;
import com.tfg.utils.ContextManager;

@Service
public class DataLakeService {
	
	ContextManager cm = ContextManager.getInstance();
	
	public void download(HttpSession session, String fileName){
		File myFile = new File("src/main/resources/"+fileName);
		HttpClient client = HttpClients.createDefault();
		String URL = "https://"+cm.getProperty("data_lake_name")+".azuredatalakestore.net/webhdfs/v1/"+fileName+"?op=OPEN&read=true";
		HttpGet request = new HttpGet(URL);
		System.out.println(session.getAttribute("bearer_token"));
		request.setHeader("Authorization", "Bearer "+session.getAttribute("bearer_token"));

		HttpResponse response = client.execute(request);
		HttpEntity entity = response.getEntity();
		if(entity!=null) {
			try (FileOutputStream outstream = new FileOutputStream(myFile)){
				entity.writeTo(outstream);
				
			} catch (Exception e) {
				
			}
		}


		//((Closeable) client).close();
		System.out.println("File Download Completed!!!");
	}
	
	public void listContents(){
		List<DirectoryEntry> list = client.enumerateDirectory("/a/b", 2000);
	    System.out.println("Directory listing for directory /a/b:");
	    for (DirectoryEntry entry : list) {
	        printDirectoryInfo(entry);
	    }
	}

}
