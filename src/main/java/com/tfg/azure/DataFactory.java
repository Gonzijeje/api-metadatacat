package com.tfg.azure;


import java.io.Closeable;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson;
import com.tfg.ContextManager;
import com.tfg.gson.GsonBearerToken;
import com.tfg.services.CSVReader;

@RestController
public class DataFactory {
	
	@Autowired
	CSVReader csvReader;
	
	@Autowired
	DataLakeService dlService;
	
	ContextManager cm = ContextManager.getInstance();
	
	@RequestMapping(value = "/azure/getBearerToken", method = RequestMethod.POST)
	public void getBearerToken(HttpSession session) throws IOException {
		String URL = "https://login.microsoftonline.com/"+ cm.getProperty("tenant_id") +"/oauth2/token";
		HttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(URL);	
		
		Gson gson = new Gson();
		Map<String,String> map = csvReader.getSimpleContent("src/main/resources/azure_credentials.csv");
		GsonBearerToken bt = new GsonBearerToken();
		bt.setGrant_type(map.get("grant_type"));
		bt.setClient_id(map.get("client_id"));
		bt.setClient_secret(map.get("client_secret"));
		bt.setResource(map.get("resource"));

		String json = "grant_type="+cm.getProperty("grant_type")+"&client_id="+cm.getProperty("client_id")+
				"&client_secret="+cm.getProperty("client_secret")+"&resource="+cm.getProperty("resource");
//		StringEntity postingString = new StringEntity(gson.toJson(bt));
		StringEntity postingString = new StringEntity(json);

		httppost.setEntity(postingString);
		httppost.setHeader("Content-type", "application/x-www-form-urlencoded");
		HttpResponse response = httpclient.execute(httppost);
		String body = EntityUtils.toString(response.getEntity());
		JSONObject myObject = new JSONObject(body);
		session.setAttribute("bearer_token", myObject.getString("access_token"));
		System.out.println(response.getStatusLine().getStatusCode());
		//System.out.println(bt.toString());
		System.out.println(body);
		((Closeable) httpclient).close();
	}
	
	@RequestMapping(value = "/azure/createPipeline", method = RequestMethod.PUT)
	public void createPipeline(HttpSession session, @RequestParam String PipelineName) throws IOException {
		String URL = "https://management.azure.com/subscriptions/"+cm.getProperty("subscription_id")+
				"/resourcegroups/"+cm.getProperty("resource_group")+"/providers/Microsoft.DataFactory/factories/"+
				cm.getProperty("factory_name")+"/pipelines/"+PipelineName+"?api-version="+cm.getProperty("api-version");
		HttpClient httpclient = HttpClients.createDefault();
		HttpPut httpput = new HttpPut(URL);	

		String json="{\r\n" + 
				"    \"name\": \""+PipelineName+"\",\r\n" + 
				"    \"properties\": {\r\n" + 
				"        \"activities\": [\r\n" + 
				"            {\r\n" + 
				"                \"name\": \"CopyDataFromBlobToDataLake\",\r\n" + 
				"                \"type\": \"Copy\",\r\n" + 
				"                \"policy\": {\r\n" + 
				"                    \"timeout\": \"7.00:00:00\",\r\n" + 
				"                    \"retry\": 0,\r\n" + 
				"                    \"retryIntervalInSeconds\": 30,\r\n" + 
				"                    \"secureOutput\": false,\r\n" + 
				"                    \"secureInput\": false\r\n" + 
				"                },\r\n" + 
				"                \"typeProperties\": {\r\n" + 
				"                    \"source\": {\r\n" + 
				"                        \"type\": \"BlobSource\",\r\n" + 
				"                        \"recursive\": true\r\n" + 
				"                    },\r\n" + 
				"                    \"sink\": {\r\n" + 
				"                        \"type\": \"AzureDataLakeStoreSink\"\r\n" + 
				"                    },\r\n" + 
				"                    \"enableStaging\": false\r\n" + 
				"                },\r\n" + 
				"                \"inputs\": [\r\n" + 
				"                    {\r\n" + 
				"                        \"referenceName\": \"InputCSVDataset\",\r\n" + 
				"                        \"type\": \"DatasetReference\"\r\n" + 
				"                    }\r\n" + 
				"                ],\r\n" + 
				"                \"outputs\": [\r\n" + 
				"                    {\r\n" + 
				"                        \"referenceName\": \"OutputDataset\",\r\n" + 
				"                        \"type\": \"DatasetReference\"\r\n" + 
				"                    }\r\n" + 
				"                ]\r\n" + 
				"            }\r\n" + 
				"        ]\r\n" + 
				"    },\r\n" + 
				"    \"type\": \"Microsoft.DataFactory/factories/pipelines\"\r\n" + 
				"}";
		StringEntity postingString = new StringEntity(json);

		httpput.setEntity(postingString);
		httpput.setHeader("Authorization", "Bearer "+session.getAttribute("bearer_token"));
		httpput.setHeader("Content-type", "application/json");
		HttpResponse response = httpclient.execute(httpput);
		String body = EntityUtils.toString(response.getEntity());
		System.out.println(response.getStatusLine().getStatusCode());
		System.out.println(body);
		((Closeable) httpclient).close();
	}
	
	@RequestMapping(value = "/azure/getPipeline", method = RequestMethod.GET)
	public String getPipeline(HttpSession session, @RequestParam String PipelineName) throws IOException {
		String URL = "https://management.azure.com/subscriptions/"+cm.getProperty("subscription_id")+
				"/resourcegroups/"+cm.getProperty("resource_group")+"/providers/Microsoft.DataFactory/factories/"+
				cm.getProperty("factory_name")+"/pipelines/"+PipelineName+"?api-version="+cm.getProperty("api-version");
		HttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(URL);

		httpget.setHeader("Authorization", "Bearer "+session.getAttribute("bearer_token"));
		HttpResponse response = httpclient.execute(httpget);
		String body = EntityUtils.toString(response.getEntity());
		JSONObject myObject = new JSONObject(body);
		System.out.println(response.getStatusLine().getStatusCode());
		System.out.println(body);
		((Closeable) httpclient).close();
		return ((JSONObject) ((JSONObject) myObject.getJSONObject("properties").getJSONArray("activities").get(0)).getJSONArray("inputs").get(0)).getString("referenceName");
	}
	
	@RequestMapping(value = "/azure/runTrigger", method = RequestMethod.POST)
	public void runTrigger(HttpSession session, @RequestParam String PipelineName) throws IOException {
		String datasetInput = getPipeline(session, PipelineName);
		String fileName = getDataset(session,datasetInput);
		String URL = "https://management.azure.com/subscriptions/"+cm.getProperty("subscription_id")+
				"/resourcegroups/"+cm.getProperty("resource_group")+"/providers/Microsoft.DataFactory/factories/"+
				cm.getProperty("factory_name")+"/pipelines/"+PipelineName+"/createRun?api-version="+cm.getProperty("api-version");
		HttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(URL);
		
		httppost.setHeader("Authorization", "Bearer "+session.getAttribute("bearer_token"));
		HttpResponse response = httpclient.execute(httppost);
		String body = EntityUtils.toString(response.getEntity());
		System.out.println(response.getStatusLine().getStatusCode());
		System.out.println(body);
		((Closeable) httpclient).close();
		dlService.download(session, fileName);
	}
	
	@RequestMapping(value = "/azure/setDataset", method = RequestMethod.PUT)
	public void setDataset(HttpSession session, @RequestParam String DatasetName, String FileName) throws IOException {
		String URL = "https://management.azure.com/subscriptions/"+cm.getProperty("subscription_id")+
				"/resourcegroups/"+cm.getProperty("resource_group")+"/providers/Microsoft.DataFactory/factories/"+
				cm.getProperty("factory_name")+"/datasets/"+DatasetName+"?api-version="+cm.getProperty("api-version");
		HttpClient httpclient = HttpClients.createDefault();
		HttpPut httpput = new HttpPut(URL);

		String json ="{"
				+ "\"name\": \""+DatasetName+"\","
				+ "\"properties\": {"
				+ "		\"linkedServiceName\": {"
				+ "			\"referenceName\": \"AzureStorageLinkedService\","
				+ "			\"type\": \"LinkedServiceReference\" },"
				+ "		\"type\": \"AzureBlob\","
				+ "		\"typeProperties\": {"
				+ "			\"fileName\": \""+FileName+"\","
				+ "			\"folderPath\": \"datainput-csv\" } },"
				+ "\"type\": \"Microsoft.DataFactory/factories/datasets\" }";
		StringEntity postingString = new StringEntity(json);

		httpput.setEntity(postingString);
		httpput.setHeader("Authorization", "Bearer "+session.getAttribute("bearer_token"));
		httpput.setHeader("Content-type", "application/json");
		System.out.println(EntityUtils.toString(httpput.getEntity()));
		HttpResponse response = httpclient.execute(httpput);
		String body = EntityUtils.toString(response.getEntity());
		System.out.println(response.getStatusLine().getStatusCode());
		System.out.println(body);
		((Closeable) httpclient).close();
	}
	
	@RequestMapping(value = "/azure/getDataset", method = RequestMethod.GET)
	public String getDataset(HttpSession session, @RequestParam String DatasetName) throws IOException {
		String URL = "https://management.azure.com/subscriptions/"+cm.getProperty("subscription_id")+
				"/resourcegroups/"+cm.getProperty("resource_group")+"/providers/Microsoft.DataFactory/factories/"+
				cm.getProperty("factory_name")+"/datasets/"+DatasetName+"?api-version="+cm.getProperty("api-version");
		HttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(URL);

		httpget.setHeader("Authorization", "Bearer "+session.getAttribute("bearer_token"));
		HttpResponse response = httpclient.execute(httpget);
		String body = EntityUtils.toString(response.getEntity());
		JSONObject myObject = new JSONObject(body);
		System.out.println(response.getStatusLine().getStatusCode());
		System.out.println(body);
		((Closeable) httpclient).close();
		return myObject.getJSONObject("properties").getJSONObject("typeProperties").getString("fileName");
	}

}
