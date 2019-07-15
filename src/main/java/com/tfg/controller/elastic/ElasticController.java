package com.tfg.controller.elastic;

import java.util.List;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tfg.services.elastic.ElasticService;

/**
 * Clase encargada de procesar las peticiones relacionadas con el sistema Elasticsearch
 * @author yeahb
 *
 */
@RestController
@RequestMapping(value="/elastic")
public class ElasticController {

	/**
	 * Servicio para realizar operaciones sobre el servidor de Elasticsearch
	 */
	@Autowired
	ElasticService elasticService;
	
	/**
	 * Método que procesa la petición para conectarse al servidor de Elasticsearch en el puerto 9200
	 * @param session
	 * @return respuesta HTTP con el código 200 (OK) y avisando de que la conexión se ha establecido
	 */
	@RequestMapping(value = "/connect", method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> connect(HttpSession session){
		elasticService.connect(session);
		return new ResponseEntity<String>( "{\"response\": \"Conexión establecida con Elasticsearch\" }",
				HttpStatus.OK);
	}
	
	/**
	 * Método que procesa la petición para crear un índice en el servidor de Elasticsearch. En el índice
	 * se indexarán los documentos procesados en las canalizaciones.
	 * @param session
	 * @return respuesta HTTP con el código 201 (CREATED) avisando de que el indice se ha creado con exito
	 */
	@RequestMapping(value = "/createIndex", method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createIndex(HttpSession session){
		elasticService.createIndex(session);
		return new ResponseEntity<String>( "{\"response\": \"Indice creado con exito\" }",
				HttpStatus.CREATED);
	}
	
	/**
	 * Método que procesa la petición para filtrar los documentos por su contenido en base a una cadena de texto pasada
	 * como parámetro.
	 * @param session
	 * @param text Cadena de texto por la que se desean filtrar los documentos, archivos de texto.
	 * @return respuesta HTTP 200 (OK) y una lista en formato JSON con los documentos encontrados tras buscar
	 * por el filtro. Primero aparecen aquellos resultados con mayor grado de coincidencia.
	 */
	@RequestMapping(value = "/match", method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> matchQuery(HttpSession session, @RequestParam String text){
		List<JSONObject> result = elasticService.matchQuery(session, text);
		return new ResponseEntity<String>( "{\"response\":"+ result.toString()+"}",
				HttpStatus.OK);
	}

}
