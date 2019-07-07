package com.tfg.controller.azure;


import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tfg.services.azure.DataFactoryService;
import com.tfg.services.model.DatasetModel;
import com.tfg.services.model.PipelineModel;

@RestController
@RequestMapping(value = "/azure")
public class DataFactoryController {

	@Autowired
	DataFactoryService factoryService;

	@RequestMapping(value = "/getBearerToken", method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getBearerToken(HttpSession session){
		JSONObject response = factoryService.getBearerToken(session);
//		return new ResponseEntity<JSONObject>(response, HttpStatus.OK);
		return new ResponseEntity<String>( "{\"response\":"+response.toString()+"}",
				HttpStatus.OK);
	}

	@RequestMapping(value = "/createPipeline", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createPipeline(HttpSession session, @Validated @RequestBody PipelineModel pipelineModel){
		JSONObject response = factoryService.createPipeline(session, pipelineModel);
		return new ResponseEntity<String>("{\"response\":"+response.toString()+"}",
				HttpStatus.CREATED);
	}

	@RequestMapping(value = "/getPipeline", method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getPipeline(HttpSession session, @RequestParam String PipelineName){
		JSONObject response = factoryService.getPipeline(session, PipelineName);
		return new ResponseEntity<String>("{\"response\":"+response.toString()+"}",
				HttpStatus.OK);
	}

	@RequestMapping(value = "/runTrigger", method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> runTrigger(HttpSession session, @RequestParam String PipelineName){
		factoryService.runTrigger(session, PipelineName);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@RequestMapping(value = "/setDataset", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> setDataset(HttpSession session, @Validated @RequestBody DatasetModel datasetModel){
		JSONObject response = factoryService.setDataset(session, datasetModel);
		return new ResponseEntity<String>("{\"response\":"+response.toString()+"}",
				HttpStatus.CREATED);
	}

	@RequestMapping(value = "/getDataset", method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getDataset(HttpSession session, @RequestParam String DatasetName){
		JSONObject response = factoryService.getDataset(session, DatasetName);
//		return new ResponseEntity<JSONObject>(response, HttpStatus.OK);
		return new ResponseEntity<String>( "{\"response\":"+response.toString()+"}",
				HttpStatus.OK);
	}

}
