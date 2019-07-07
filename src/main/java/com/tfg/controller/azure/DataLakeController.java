package com.tfg.controller.azure;

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

import com.tfg.services.azure.DataLakeService;

@RestController
@RequestMapping(value = "/azure")
public class DataLakeController {
	
	@Autowired
	DataLakeService dlService;
	
	@RequestMapping(value = "/download", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONObject> download(HttpSession session, @RequestParam String pathFile){
		dlService.download(session, pathFile);
		return new ResponseEntity<JSONObject>(HttpStatus.OK);
	}

}
