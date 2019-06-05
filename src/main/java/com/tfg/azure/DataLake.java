package com.tfg.azure;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.servlet.http.HttpSession;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataLake {
	
	@RequestMapping(value = "/azure/download", method = RequestMethod.GET)
	public void download(HttpSession session) throws IOException {
		File myFile = new File("src/main/resources/CreatePipeline.txt");
		HttpClient client = HttpClients.createDefault();
		String URL = "https://adlakegcv.azuredatalakestore.net/webhdfs/v1/CreatePipeline.txt?op=OPEN&read=true";
		HttpGet request = new HttpGet("https://adlakegcv.azuredatalakestore.net/webhdfs/v1/CreatePipeline.txt?op=OPEN&read=true");
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

		int responseCode = response.getStatusLine().getStatusCode();

		String body = EntityUtils.toString(response.getEntity());
		System.out.println(response.getStatusLine().getStatusCode());
		System.out.println(body);

		//FileUtils.copyURLToFile(request.getURI(), new File("src/main/resources/CreatePipeline.txt"));



		//((Closeable) client).close();
		System.out.println("File Download Completed!!!");
	}

    

}
