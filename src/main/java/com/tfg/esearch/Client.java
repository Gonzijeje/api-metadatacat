package com.tfg.esearch;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpHost;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.get.GetResult;

import com.tfg.factory.ExceptionFactory;
import com.tfg.factory.ExceptionFactory.Errors;

public class Client {

	public static RestHighLevelClient client;

	public void connect() {
		try {
			client = new RestHighLevelClient(
					RestClient.builder(
							new HttpHost("localhost", 9200, "http"),
							new HttpHost("localhost", 9201, "http")));
		} catch (Exception e) {
			throw ExceptionFactory.getError(Errors.ELASTICSEARCH_CONNECT);
		}
			
	}

	public void createIndex() throws IOException{
		CreateIndexRequest request = new CreateIndexRequest("asset");
		request.settings(Settings.builder() 
				.put("index.number_of_shards", 3)
				.put("index.number_of_replicas", 2)
				);
		Map<String, Object> message = new HashMap<>();
		message.put("type", "text");
		Map<String, Object> properties = new HashMap<>();
		properties.put("message", message);
		Map<String, Object> mapping = new HashMap<>();
		mapping.put("properties", properties);
		request.mapping(mapping);

		CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
		boolean acknowledged = createIndexResponse.isAcknowledged(); 
		boolean shardsAcknowledged = createIndexResponse.isShardsAcknowledged();
		System.out.println("UNO: "+acknowledged+" , DOS: "+shardsAcknowledged);
	}

	public void indexApi(Map<String, Object> payload) throws IOException {
		Map<String, Object> jsonMap = new HashMap<>();
		jsonMap.put("Grupo básicos",payload);
		IndexRequest indexRequest = new IndexRequest("asset").id(payload.get("codigo").toString()).source(jsonMap);
		IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
		if (indexResponse.getResult() == DocWriteResponse.Result.CREATED) {
			System.out.println("CREADO");
		} else if (indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {
			System.out.println("UPDATEADO");
		}
		ReplicationResponse.ShardInfo shardInfo = indexResponse.getShardInfo();
		if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
			System.out.println("Handle the situation where number of successful shards is less than total shards");
		}
		if (shardInfo.getFailed() > 0) {
			for (ReplicationResponse.ShardInfo.Failure failure :
				shardInfo.getFailures()) {
				String reason = failure.reason();
				System.out.println(reason.toString());
			}
		}
	}
	
	public void addMetadata(Map<String,Map<String,Object>> payload, String codigo) throws IOException {
		Map<String, Object> jsonMap = new HashMap<>();
		for(String key:payload.keySet()) {
			jsonMap.put(key, payload.get(key));
		}
		UpdateRequest request = new UpdateRequest("asset",codigo).doc(jsonMap);
		UpdateResponse updateResponse = client.update(
		        request, RequestOptions.DEFAULT);
		GetResult result = updateResponse.getGetResult();
		if(result==null) {
			System.out.println("ESTO ES NULL");
		}
		else if(result.isExists()) {
			System.out.println(result.sourceAsString());
		}
	}
	
	public void disconnect() throws IOException {
		client.close();
	}

}
