package com.tfg.esearch;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Component;

@Component
public class Search {
	
	public void matchAll() {
		SearchRequest searchRequest = new SearchRequest("asset");
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder(); 
		searchSourceBuilder.query(QueryBuilders.matchAllQuery());
		searchRequest.source(searchSourceBuilder);

		SearchResponse searchResponse;
		try {
			searchResponse = Client.client.search(searchRequest, RequestOptions.DEFAULT);
			RestStatus status = searchResponse.status();
			System.out.println("Status query: "+status);
			SearchHits hits = searchResponse.getHits();
			SearchHit[] searchHits = hits.getHits();
			for (SearchHit hit : searchHits) {
				String sourceAsString = hit.getSourceAsString();
				System.out.println(sourceAsString);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void query(String value) {
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
		sourceBuilder.query(QueryBuilders.termQuery("code", value)); 
		sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
	}
	

}
