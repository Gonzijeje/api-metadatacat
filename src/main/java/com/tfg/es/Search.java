package com.tfg.es;

import java.io.IOException;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

public class Search {
	
	public void matchAll() throws IOException {
		SearchRequest searchRequest = new SearchRequest("asset");
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder(); 
		searchSourceBuilder.query(QueryBuilders.matchAllQuery());
		searchRequest.source(searchSourceBuilder);
		
		SearchResponse searchResponse = Client.client.search(searchRequest, RequestOptions.DEFAULT);
		RestStatus status = searchResponse.status();
		System.out.println("Status query: "+status);
		SearchHits hits = searchResponse.getHits();
		SearchHit[] searchHits = hits.getHits();
		for (SearchHit hit : searchHits) {
			String sourceAsString = hit.getSourceAsString();
			System.out.println(sourceAsString);
		}

	}

}
