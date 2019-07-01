package com.tfg.services.azure;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;

import javax.servlet.http.HttpSession;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.tfg.exceptions.ExceptionFactory;
import com.tfg.exceptions.ExceptionFactory.Errors;
import com.tfg.services.model.DatasetModel;
import com.tfg.services.model.PipelineModel;
import com.tfg.utils.ContextManager;
import com.tfg.utils.JsonRequests;

@Service
public class DataFactoryService {
	
	ContextManager cm = ContextManager.getInstance();
	
	@Autowired
	AzureStorageService storageService;
	
	public JSONObject getBearerToken(HttpSession session){
		String URL = "https://login.microsoftonline.com/"+ cm.getProperty("tenant_id") +"/oauth2/token";
		
		HttpPost httppost = new HttpPost(URL);
		
		String json = "grant_type="+cm.getProperty("grant_type")+"&client_id="+cm.getProperty("client_id")+
				"&client_secret="+cm.getProperty("client_secret")+"&resource="+cm.getProperty("resource");
		StringEntity postingString;
		try {
			postingString = new StringEntity(json);
		} catch (UnsupportedEncodingException e) {
			throw ExceptionFactory.getError(Errors.ENTITY_NOT_FOUND);
		}
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
	
	public JSONObject getDataset(HttpSession session, String DatasetName){
		String URL = "https://management.azure.com/subscriptions/"+cm.getProperty("subscription_id")+
				"/resourcegroups/"+cm.getProperty("resource_group")+"/providers/Microsoft.DataFactory/factories/"+
				cm.getProperty("factory_name")+"/datasets/"+DatasetName+"?api-version="+cm.getProperty("api-version");

		HttpGet httpget = new HttpGet(URL);

		httpget.setHeader("Authorization", "Bearer "+session.getAttribute("bearer_token"));
		HttpResponse response = cm.getHttpClient().execute(httpget);
		String body = EntityUtils.toString(response.getEntity());
		JSONObject myObject = new JSONObject(body);
		
		System.out.println(response.getStatusLine().getStatusCode());
		System.out.println(body);
		return myObject;
	}
	
	public JSONObject getPipeline(HttpSession session, String PipelineName){
		String URL = "https://management.azure.com/subscriptions/"+cm.getProperty("subscription_id")+
				"/resourcegroups/"+cm.getProperty("resource_group")+"/providers/Microsoft.DataFactory/factories/"+
				cm.getProperty("factory_name")+"/pipelines/"+PipelineName+"?api-version="+cm.getProperty("api-version");
		
		HttpGet httpget = new HttpGet(URL);

		httpget.setHeader("Authorization", "Bearer "+session.getAttribute("bearer_token"));
		HttpResponse response = cm.getHttpClient().execute(httpget);
		String body = EntityUtils.toString(response.getEntity());
		JSONObject myObject = new JSONObject(body);
		
		System.out.println(response.getStatusLine().getStatusCode());
		System.out.println(body);
		return myObject;
	}
	
	public void runTrigger(HttpSession session, String PipelineName){
		JSONObject pipelineInput = getPipeline(session, PipelineName);
		String datasetInputName = ((JSONObject) ((JSONObject) pipelineInput.getJSONObject("properties").getJSONArray("activities").get(0)).getJSONArray("inputs").get(0)).getString("referenceName");
		JSONObject datasetInput = getDataset(session,datasetInputName);
		String fileName = datasetInput.getJSONObject("properties").getJSONObject("typeProperties").getString("fileName");
		String containerName = datasetInput.getJSONObject("properties").getJSONObject("typeProperties").getString("folderPath");
				
		String URL = "https://management.azure.com/subscriptions/"+cm.getProperty("subscription_id")+
				"/resourcegroups/"+cm.getProperty("resource_group")+"/providers/Microsoft.DataFactory/factories/"+
				cm.getProperty("factory_name")+"/pipelines/"+PipelineName+"/createRun?api-version="+cm.getProperty("api-version");
		
		HttpPost httppost = new HttpPost(URL);
		
		httppost.setHeader("Authorization", "Bearer "+session.getAttribute("bearer_token"));
		HttpResponse response = cm.getHttpClient().execute(httppost);
		String body = EntityUtils.toString(response.getEntity());
		
		System.out.println(response.getStatusLine().getStatusCode());
		System.out.println(body);
		
		storageService.downloadFile(containerName, fileName);
	}
	
	public JSONObject setDataset(HttpSession session, DatasetModel datasetModel){
		String URL = "https://management.azure.com/subscriptions/"+cm.getProperty("subscription_id")+
				"/resourcegroups/"+cm.getProperty("resource_group")+"/providers/Microsoft.DataFactory/factories/"+
				cm.getProperty("factory_name")+"/datasets/"+DatasetName+"?api-version="+cm.getProperty("api-version");
		
		HttpPut httpput = new HttpPut(URL);

		String json = String.format(JsonRequests.CREATE_DATASET, DatasetName, FileName, DataSource);
		StringEntity postingString = new StringEntity(json);

		httpput.setEntity(postingString);
		httpput.setHeader("Authorization", "Bearer "+session.getAttribute("bearer_token"));
		httpput.setHeader("Content-type", "application/json");
		HttpResponse response = cm.getHttpClient().execute(httpput);
		String body = EntityUtils.toString(response.getEntity());
		JSONObject myObject = new JSONObject(body);
		
		System.out.println(response.getStatusLine().getStatusCode());
		System.out.println(body);
		return myObject;
	}
	
	public JSONObject createPipeline(HttpSession session, PipelineModel pipelineModel){
		String URL = "https://management.azure.com/subscriptions/"+cm.getProperty("subscription_id")+
				"/resourcegroups/"+cm.getProperty("resource_group")+"/providers/Microsoft.DataFactory/factories/"+
				cm.getProperty("factory_name")+"/pipelines/"+PipelineName+"?api-version="+cm.getProperty("api-version");
		HttpPut httpput = new HttpPut(URL);	

		String json = MessageFormat.format(JsonRequests.CREATE_PIPELINE, PipelineName, DatasetInput);
		StringEntity postingString = new StringEntity(json);

		httpput.setEntity(postingString);
		httpput.setHeader("Authorization", "Bearer "+session.getAttribute("bearer_token"));
		httpput.setHeader("Content-type", "application/json");
		HttpResponse response = cm.getHttpClient().execute(httpput);
		String body = EntityUtils.toString(response.getEntity());
		JSONObject myObject = new JSONObject(body);
		
		System.out.println(response.getStatusLine().getStatusCode());
		System.out.println(body);
		return myObject;
	}

}
