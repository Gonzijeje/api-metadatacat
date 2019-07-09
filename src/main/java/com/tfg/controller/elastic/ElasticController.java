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

@RestController
@RequestMapping(value="/elastic")
public class ElasticController {

	@Autowired
	ElasticService elasticService;
	
	@RequestMapping(value = "/connect", method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> connect(HttpSession session){
		elasticService.connect(session);
		return new ResponseEntity<String>( "{\"response\": \"Conexión establecida con Elasticsearch\" }",
				HttpStatus.OK);
	}
	
	@RequestMapping(value = "/index", method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createIndex(HttpSession session){
		elasticService.createIndex(session);
		return new ResponseEntity<String>( "{\"response\": \"Índice creado con éxito\" }",
				HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/match", method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> matchQuery(HttpSession session, @RequestParam String text){
		List<JSONObject> result = elasticService.matchQuery(session, text);
		return new ResponseEntity<String>( "{\"response\":"+ result.toString()+"}",
				HttpStatus.OK);
	}
	
	@RequestMapping(value = "/indexx", method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> indexar(HttpSession session, @RequestParam String code,
			@RequestParam String description, @RequestParam String content){
		elasticService.index(session, code, description, content);
//		List<JSONObject> result = elasticService.matchQuery(session, text);
		return new ResponseEntity<String>(
				HttpStatus.OK);
	}

}
