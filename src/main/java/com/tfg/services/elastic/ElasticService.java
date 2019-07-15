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

/**
 * Clase donde se definen las implementaciones de las operaciones relacionadas con el servicio Elasticsearch
 * @author gcollada
 *
 */
@Service
public class ElasticService {

	/**
	 * Cliente HTTP proporcionado por la libería REST de Azure, para realizar llamadas
	 * y operaciones sobre el servidor de Elasticsearch
	 */
	public static RestHighLevelClient client;
	private static final String defaultIndex = "assets";

	/**
	 * Método que permite conectarse al servidor de Elasticsearch mediante una petición HTTP en el puerto 9200.
	 * Se añade un atributo de sesión indicando que se la conexión se ha establecido
	 * @param session
	 */
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

	/**
	 * Método que permite crear un índice de documentos en el servidor de Elasticsearch, donde se almacenarán
	 * posteriormente los documentos procesados en las canalizaciones de datos. Por defecto tendrá el nombre de
	 * assets.
	 * @param session
	 */
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

	/**
	 * Método que permite indexar un documento procesado en las canalizaciones en el índice creado en el
	 * sevidor de Elasticsearch.
	 * @param session
	 * @param code Código del documento a indexar
	 * @param description Descripción del documento a indexar
	 * @param content Contenido de texto del documento a indexar
	 */
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

	/**
	 * Método para cerrar la coneión con el servidor de Elasticsearch
	 * @throws IOException
	 */
	public void disconnect() throws IOException {
		client.close();
	}

	/**
	 * Método que permite realizar consultas de búsquedas para encontrar documentos cuya descripción o contenido
	 * coincida con el parámetro proporcionado por el usuario. Se trata de filtrar el contenido de los documentos
	 * por la consulta de búsqueda, edvolviendo los que la contienen total o parcialmente
	 * @param session
	 * @param text Cadena de texto por la que se desean filtrar los documentos
	 * @return Lista de objetos JSON con la información de los documentos filtrados. Aparecen primero aquellos
	 * con mayor índice de coincidencia con la consulta
	 */
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
