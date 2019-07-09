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

@Service
public class DataLakeService {
	
	ContextManager cm = ContextManager.getInstance();
	private static final String defaultPath = "src/main/resources/";
	
	@Autowired
	FileInfo fileService;
	
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
				System.out.println("CSV creado");
				fileService.getMetadata(session, pathFile,new NewAsset());
			}
		} catch (IOException | ParseException e) {
			throw  ExceptionFactory.getError(Errors.HTTP_CLIENT_ERROR);
		}		
	}

}
