package com.tfg.services.elastic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.tfg.exceptions.ExceptionFactory;
import com.tfg.exceptions.ExceptionFactory.Errors;

@Service
public class ElasticService {

	public static RestHighLevelClient client;
	private static final String defaultIndex = "assets";

	public void connect(HttpSession session) {
		try {
			client = new RestHighLevelClient(
					RestClient.builder(
							new HttpHost("localhost", 9200, "http"),
							new HttpHost("localhost", 9201, "http")));
			session.setAttribute("elastic_connect", "true");
		} catch (Exception e) {
			throw ExceptionFactory.getError(Errors.ELASTICSEARCH_CONNECT);
		}
	}

	public void createIndex(HttpSession session){
		CreateIndexRequest request = new CreateIndexRequest(defaultIndex);
		request.settings(Settings.builder() 
				.put("index.number_of_shards", 3)
				.put("index.number_of_replicas", 2)
				);
		try {
			client.indices().create(request, RequestOptions.DEFAULT);
		} catch (IOException e) {
			throw ExceptionFactory.getError(Errors.ELASTICSEARCH_CONNECT);
		}

	}

	public void index(HttpSession session, String code, String description, String content) {
		if(session.getAttribute("elastic_connect")=="true") {
			Map<String, Object> jsonMap = new HashMap<>();
			jsonMap.put("code",code);
			jsonMap.put("description",description);
			jsonMap.put("content",content);
			IndexRequest indexRequest = new IndexRequest(defaultIndex)
					.id(code).source(jsonMap);
			try {
				client.index(indexRequest, RequestOptions.DEFAULT);
			} catch (IOException e) {
				throw ExceptionFactory.getError(Errors.ELASTICSEARCH_CONNECT);
			}
		}
	}

	public void disconnect() throws IOException {
		client.close();
	}

	public List<JSONObject> matchQuery(HttpSession session, String text) {
		List<JSONObject> assets = new ArrayList<JSONObject>();
		SearchRequest searchRequest = new SearchRequest(defaultIndex);
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.query(QueryBuilders.multiMatchQuery(text, "description","content"));
		searchRequest.source(sourceBuilder);
		try {
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			for(SearchHit hit : searchResponse.getHits().getHits()) {
				String sourceAsString = hit.getSourceAsString();
				assets.add(new JSONObject(sourceAsString));
			}
			return assets;
		} catch (IOException | NullPointerException e) {
			throw ExceptionFactory.getError(Errors.ELASTICSEARCH_CONNECT);
		}
	}
}
