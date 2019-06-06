package com.tfg.azure;

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

@Service
public class DataLakeService {
	
	public void download(HttpSession session, String FileName) throws IOException {
		File myFile = new File("src/main/resources/CreatePipeline.txt");
		HttpClient client = HttpClients.createDefault();
		String URL = "https://adlakegcv.azuredatalakestore.net/webhdfs/v1/"+FileName+"?op=OPEN&read=true";
		HttpGet request = new HttpGet(URL);
		System.out.println(session.getAttribute("bearer_token"));
		request.setHeader("Authorization", "Bearer "+session.getAttribute("bearer_token"));

		HttpResponse response = client.execute(request);
		HttpEntity entity = response.getEntity();
		if(entity!=null) {
			try (FileOutputStream outstream = new FileOutputStream(myFile)){
				entity.writeTo(outstream);
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

//		int responseCode = response.getStatusLine().getStatusCode();

//		String body = EntityUtils.toString(response.getEntity());
//		System.out.println(response.getStatusLine().getStatusCode());
//		System.out.println(body);

		//FileUtils.copyURLToFile(request.getURI(), new File("src/main/resources/CreatePipeline.txt"));



		//((Closeable) client).close();
		System.out.println("File Download Completed!!!");
	}

}
