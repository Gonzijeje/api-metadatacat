package com.tfg.azure;


import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.tfg.gson.GsonBearerToken;
import com.tfg.model.Grupo;
import com.tfg.services.CSVReader;

@RestController
public class DataFactory {
	
	CSVReader csvReader = new CSVReader();
	
	/*class Test {
	    public static void main(String[] args) throws Exception {
	        URL url = new URL("http://example.net/new-message.php");
	        Map<String,Object> params = new LinkedHashMap<>();
	        params.put("name", "Freddie the Fish");
	        params.put("email", "fishie@seamail.example.com");
	        params.put("reply_to_thread", 10394);
	        params.put("message", "Shark attacks in Botany Bay have gotten out of control. We need more defensive dolphins to protect the schools here, but Mayor Porpoise is too busy stuffing his snout with lobsters. He's so shellfish.");

	        StringBuilder postData = new StringBuilder();
	        for (Map.Entry<String,Object> param : params.entrySet()) {
	            if (postData.length() != 0) postData.append('&');
	            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
	            postData.append('=');
	            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
	        }
	        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

	        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
	        conn.setRequestMethod("POST");
	        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
	        conn.setDoOutput(true);
	        conn.getOutputStream().write(postDataBytes);

	        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

	        for (int c; (c = in.read()) >= 0;)
	            System.out.print((char)c);
	    }
	}*/
	
	@RequestMapping(value = "/azure/getBearerToken", method = RequestMethod.POST)
	public void getBearerToken() throws IOException {
		String URL = "https://login.microsoftonline.com/bf43569e-dcd7-46e9-9f91-5e81bda90abb" + 
				"/oauth2/token";
		HttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(URL);	
		
		Gson gson = new Gson();
		Map<String,String> map = csvReader.getSimpleContent("src/main/resources/azure_credentials.csv");
		GsonBearerToken bt = new GsonBearerToken();
		bt.setGrant_type(map.get("grant_type"));
		bt.setClient_id(map.get("client_id"));
		bt.setClient_secret(map.get("client_secret"));
		bt.setResource(map.get("resource"));

		String json = "grant_type=client_credentials&client_id=92d9720c-db25-4e11-bd7f-70e98705e825&client_secret=*Apr[7xUZQB1Cp=mBu14lppdQYIxSdy*&resource=https://management.azure.com/";
//		StringEntity postingString = new StringEntity(gson.toJson(bt));
		StringEntity postingString = new StringEntity(json);

		httppost.setEntity(postingString);
		httppost.setHeader("Content-type", "application/x-www-form-urlencoded");
		HttpResponse response = httpclient.execute(httppost);
		String body = EntityUtils.toString(response.getEntity());
		System.out.println(response.getStatusLine().getStatusCode());
		System.out.println(bt.toString());
		System.out.println(body);
		((Closeable) httpclient).close();
	}
	
	@RequestMapping(value = "/azure/createPipeline", method = RequestMethod.PUT)
	public void createPipeline() throws IOException {
		String URL = "https://management.azure.com/subscriptions/{SubscriptionID}/resourcegroups/{ResourceGroupName}/providers/Microsoft.DataFactory/datafactories/{DataFactoryName}/datapipelines/{PipelineName}?api-version={api-version}	";
		HttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(URL);	
		
		Gson gson = new Gson();
		Map<String,String> map = csvReader.getSimpleContent("src/main/resources/azure_credentials.csv");
		GsonBearerToken bt = new GsonBearerToken();
		bt.setGrant_type(map.get("grant_type"));
		bt.setClient_id(map.get("client_id"));
		bt.setClient_secret(map.get("client_secret"));
		bt.setResource(map.get("resource"));

		String json = "grant_type=client_credentials&client_id=92d9720c-db25-4e11-bd7f-70e98705e825&client_secret=*Apr[7xUZQB1Cp=mBu14lppdQYIxSdy*&resource=https://management.azure.com/";
//		StringEntity postingString = new StringEntity(gson.toJson(bt));
		StringEntity postingString = new StringEntity(json);

		httppost.setEntity(postingString);
		httppost.setHeader("Content-type", "application/x-www-form-urlencoded");
		HttpResponse response = httpclient.execute(httppost);
		String body = EntityUtils.toString(response.getEntity());
		System.out.println(response.getStatusLine().getStatusCode());
		System.out.println(bt.toString());
		System.out.println(body);
		((Closeable) httpclient).close();
	}
	
	@RequestMapping(value = "/azure/runTrigger", method = RequestMethod.POST)
	public void runTrigger() throws IOException {
		String URL = "https://management.azure.com/subscriptions/b91e5bfa-dc94-4aa6-b81d-0546c95d98ee/resourceGroups/tfgGCV/providers/Microsoft.DataFactory/factories/adfactorygcv/pipelines/pipeline1/createRun?api-version=2018-06-01";
		HttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(URL);
		
		httppost.setHeader("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6IkhCeGw5bUFlNmd"
				+ "4YXZDa2NvT1UyVEhzRE5hMCIsImtpZCI6IkhCeGw5bUFlNmd4YXZDa2NvT1UyVEhzRE5hMCJ9.eyJhdWQiOiJodHRwczovL21hbm"
				+ "FnZW1lbnQuYXp1cmUuY29tLyIsImlzcyI6Imh0dHBzOi8vc3RzLndpbmRvd3MubmV0L2JmNDM1NjllLWRjZDctNDZlOS05ZjkxLTVlODFiZGE5MGF"
				+ "iYi8iLCJpYXQiOjE1NTk2NDU0OTEsIm5iZiI6MTU1OTY0NTQ5MSwiZXhwIjoxNTU5NjQ5MzkxLCJhaW8iOiI0MlpnWVBqOFV2cHBYYzJIRTVYZnBudn"
				+ "FIbjdqQWdBPSIsImFwcGlkIjoiOTJkOTcyMGMtZGIyNS00ZTExLWJkN2YtNzBlOTg3MDVlODI1IiwiYXBwaWRhY3IiOiIxIiwiaWRwIjoiaHR0cHM6Ly"
				+ "9zdHMud2luZG93cy5uZXQvYmY0MzU2OWUtZGNkNy00NmU5LTlmOTEtNWU4MWJkYTkwYWJiLyIsIm9pZCI6ImUzZjcxNWM1LWQ5YzctNDQ2Yy1hMjVjLTg"
				+ "2MTQ0NzU1NzVmNyIsInN1YiI6ImUzZjcxNWM1LWQ5YzctNDQ2Yy1hMjVjLTg2MTQ0NzU1NzVmNyIsInRpZCI6ImJmNDM1NjllLWRjZDctNDZlOS05Zjkx"
				+ "LTVlODFiZGE5MGFiYiIsInV0aSI6ImVMNFBtalV3M0Vpb0pnbTg4bkIzQUEiLCJ2ZXIiOiIxLjAifQ.lXySID9FECkIrRrRpeOFbHgI0ngGZ69_UQrZ_6SRY_Y-jlpZqVhC_7pUv1ce72"
				+ "Lr0_kBoWIz1eVelh4m9hAgi9Tgl_3T2M8IHkHJ_6rBiOF5ltJVf5QpOsxw5bi5Txsoq5hwblzQL6aNWtRG0VxoQgVcF3nQCuj-jDVVmBMewsuwAAw2Mg8fb1BQd2Hv4Je4n2cDwf7f6aoc"
				+ "WOMxxp3zwUjXe7gnY5KuJayrcLBmyS1J8X_utp0dr0nBozqJv8lIEZYR06PesEdw3SV3yJn4zHTOfGhnHh9JR0pBObMhqb4806sYus2_6HRjy9Sxrkt6yiVDvYMi2iutRs8kGKQWxw");
		HttpResponse response = httpclient.execute(httppost);
		String body = EntityUtils.toString(response.getEntity());
		System.out.println(response.getStatusLine().getStatusCode());
		System.out.println(body);
		((Closeable) httpclient).close();
	}
	
	/*
	 * Get AAD Token

	URI:
		POST https://login.microsoftonline.com/:tenant_id/oauth2/token

	URI PARAMS:
		tenant_id: bf43569e-dcd7-46e9-9f91-5e81bda90abb

	BODY:
	
		grant_type: client_credentials
		client_id: 92d9720c-db25-4e11-bd7f-70e98705e825
		client_secret: *Apr[7xUZQB1Cp=mBu14lppdQYIxSdy*
		resource: https://management.azure.com/

	TESTS
		var json = JSON.parse(respondeBody);

Get Data Factories in Subscription
	URI:
		GET https://management.azure.com/subscriptions/{subscriptionId}/providers/Microsoft.DataFactory/factories?api-version=2018-06-01

	URI PARAMS:
		subscriptionId: b91e5bfa-dc94-4aa6-b81d-0546c95d98ee
		api-version: 

	HEADERS:
		Authorization: Bearer {{bearerToken}}

	TESTS:
		var json = JSON.parse(responseBody);
		tests["Get Data Factories in Subscription"] = !json.error && responseBody !== '' && responseBody !== '{}';
	 */

}
