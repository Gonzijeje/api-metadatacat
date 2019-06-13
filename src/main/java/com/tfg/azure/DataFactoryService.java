package com.tfg.azure;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.tfg.ContextManager;

@Service
public class DataFactoryService {
	
	ContextManager cm = ContextManager.getInstance();
	
	public JSONObject getBearerToken(HttpSession session) throws IOException {
		String URL = "https://login.microsoftonline.com/"+ cm.getProperty("tenant_id") +"/oauth2/token";
		HttpPost httppost = new HttpPost(URL);
		String json = "grant_type="+cm.getProperty("grant_type")+"&client_id="+cm.getProperty("client_id")+
				"&client_secret="+cm.getProperty("client_secret")+"&resource="+cm.getProperty("resource");
		StringEntity postingString = new StringEntity(json);
		httppost.setEntity(postingString);
		httppost.setHeader("Content-type", "application/x-www-form-urlencoded");
		HttpResponse response = cm.getHttpClient().execute(httppost);
		String body = EntityUtils.toString(response.getEntity());
		JSONObject myObject = new JSONObject(body);
		session.setAttribute("bearer_token", myObject.getString("access_token"));
		System.out.println(response.getStatusLine().getStatusCode());
		System.out.println(body);
		return myObject;
	}

}
