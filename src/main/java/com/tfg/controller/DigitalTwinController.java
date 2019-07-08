package com.tfg.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tfg.model.DigitalTwin;
import com.tfg.services.DigitalTwinService;
import com.tfg.services.model.GroupFieldModel;
import com.tfg.services.model.NewTwin;
import com.tfg.services.model.TwinModel;

@RestController
@RequestMapping(value = "/twins")
public class DigitalTwinController {
	
	@Autowired
	DigitalTwinService twinService;
	
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TwinModel> registerDigitalTwin(@Validated @RequestBody NewTwin newDigitalTwin){
		DigitalTwin twin = twinService.create(newDigitalTwin);
		twinService.add(newDigitalTwin, twin);
		TwinModel model = twinService.findByCodigo(twin.getCodigo());
		return new ResponseEntity<TwinModel>(model, HttpStatus.CREATED);
	}
	
	
	@RequestMapping(value = "/getDigitalTwins", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TwinModel>> getDigitalAssets(@RequestParam(required=false) Map<String,Object> allRequestParams){
		List<TwinModel> result = twinService.getDigitalTwinsByFilters(allRequestParams);
		return new ResponseEntity<List<TwinModel>>(result, HttpStatus.OK);
    }
	
	
	@RequestMapping(value = "{code}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TwinModel> deleteDigitalTwin(@PathVariable String code){
		twinService.delete(code);
		return new ResponseEntity<TwinModel>(HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(value = "/addMetadata/{code}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TwinModel> addMetadataToDigitalTwin(@RequestBody List<GroupFieldModel> models, 
			@PathVariable String code) {
		twinService.addMetadata(models, code);
		TwinModel model = twinService.findByCodigo(code);
		return new ResponseEntity<TwinModel>(model, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/deleteMetadata/{code}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TwinModel> deleteMetadataToDigitalTwin(@RequestBody List<GroupFieldModel> models, 
			@PathVariable String code) {
		twinService.deleteMetadata(models, code);
		TwinModel model = twinService.findByCodigo(code);
		return new ResponseEntity<TwinModel>(model, HttpStatus.OK);
	}

}
