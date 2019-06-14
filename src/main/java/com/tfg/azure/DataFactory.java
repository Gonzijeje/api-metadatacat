package com.tfg.azure;


import java.io.IOException;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.tfg.ContextManager;
import com.tfg.services.CSVReader;

@RestController
public class DataFactory {
	
	@Autowired
	CSVReader csvReader;
	
	@Autowired
	DataLakeService dlService;
	
	@Autowired
	DataFactoryService factory;
	
	ContextManager cm = ContextManager.getInstance();
	
	@RequestMapping(value = "/azure/getBearerToken", method = RequestMethod.POST)
	public ResponseEntity<String> getBearerToken(HttpSession session){
		try {
			JSONObject response = factory.getBearerToken(session);
			return new ResponseEntity<String>( "{\"response\":"+response.toString()+"}",
					HttpStatus.OK);
		} catch (IOException e) {
			return new ResponseEntity<String>( "{\"response\":\"Datos introducidos incorrectos\"}",
					HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/azure/createPipeline", method = RequestMethod.PUT)
	public ResponseEntity<String> createPipeline(HttpSession session, @RequestParam String PipelineName){
		try {
			factory.createPipeline(session, PipelineName);
			return new ResponseEntity<String>( "{\"response\":\"Datos introducidos incorrectos\"}",
					HttpStatus.BAD_REQUEST);
		} catch (IOException e) {
			return new ResponseEntity<String>( "{\"response\":\"Datos introducidos incorrectos\"}",
					HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/azure/getPipeline", method = RequestMethod.GET)
	public ResponseEntity<String> getPipeline(HttpSession session, @RequestParam String PipelineName){
		try {
			JSONObject response = factory.getPipeline(session, PipelineName);
			return new ResponseEntity<String>( "{\"response\":"+response.toString()+"}",
					HttpStatus.OK);
		} catch (IOException e) {
			return new ResponseEntity<String>( "{\"response\":\"Datos introducidos incorrectos\"}",
					HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/azure/runTrigger", method = RequestMethod.POST)
	public ResponseEntity<String> runTrigger(HttpSession session, @RequestParam String PipelineName){
		try {
			factory.runTrigger(session, PipelineName);
			return new ResponseEntity<String>( "{\"response\":\"Pipeline"+PipelineName+" ejecutada con éxito\"}",
					HttpStatus.OK);
		} catch (IOException e) {
			return new ResponseEntity<String>( "{\"response\":\"Datos introducidos incorrectos\"}",
					HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/azure/setDataset", method = RequestMethod.PUT)
	public ResponseEntity<String> setDataset(HttpSession session, @RequestParam String DatasetName, String FileName){
		try {
			factory.setDataset(session, DatasetName, FileName);
			return new ResponseEntity<String>( "{\"response\":\"Dataset "+DatasetName+" creado\"}",
					HttpStatus.OK);
		} catch (IOException e) {
			return new ResponseEntity<String>( "{\"response\":\"Datos introducidos incorrectos\"}",
					HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/azure/getDataset", method = RequestMethod.GET)
	public ResponseEntity<String> getDataset(HttpSession session, @RequestParam String DatasetName){
		try {
			JSONObject response = factory.getDataset(session, DatasetName);
			return new ResponseEntity<String>( "{\"response\":"+response.toString()+"}",
					HttpStatus.OK);
		} catch (IOException e) {
			return new ResponseEntity<String>( "{\"response\":\"Datos introducidos incorrectos\"}",
					HttpStatus.BAD_REQUEST);
		}		
	}

}
