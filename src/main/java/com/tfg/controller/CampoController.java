package com.tfg.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tfg.model.Field;
import com.tfg.pojos.FieldModel;
import com.tfg.pojos.NewField;
import com.tfg.services.FieldService;

/**
 * 
 * @author gcollada
 *
 */
@RestController
@RequestMapping(value = "/fields")
public class CampoController {
	
	@Autowired
	FieldService fieldService;
	
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<FieldModel> registerField(@Validated @RequestBody NewField newField ) {
		try {
			Field campo = fieldService.create(newField);
			FieldModel model = fieldService.add(campo);
			return new ResponseEntity<FieldModel>(model, HttpStatus.CREATED);
		}catch (Exception e) {
			
		}
		return null; //
			
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<FieldModel> updateField(@Validated @RequestBody NewField newField ) {
		try {
			Field campo = fieldService.create(newField);
			FieldModel model = fieldService.add(campo);
			return new ResponseEntity<FieldModel>(model, HttpStatus.CREATED);
		}catch (Exception e) {
			
		}
		return null; //
			
	}
	
	@RequestMapping(value = "/{code}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<FieldModel> getFieldByCode(@PathVariable String code) {
		try {
			FieldModel model = fieldService.getCampoByCodigo(code);
			return new ResponseEntity<FieldModel>(model, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null; //

	}
	
	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FieldModel>> listFields(){
		List<FieldModel> listModels = fieldService.getCampos();
		return new ResponseEntity<List<FieldModel>>(listModels, HttpStatus.OK); //
    }
	
	@RequestMapping(value = "/{code}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<FieldModel> deleteField(@PathVariable String code){
		if(fieldService.delete(code)) {
			return new ResponseEntity<FieldModel>(HttpStatus.ACCEPTED);
		}else {
			
		}
		return null; //
	}
}
