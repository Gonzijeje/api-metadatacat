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
import com.tfg.services.FieldService;
import com.tfg.services.adapters.FieldAdapter;
import com.tfg.services.model.FieldModel;
import com.tfg.services.model.NewField;

/**
 * Clase encargada de procesar las peticiones relacionadas con el recurso de Fields (campos).
 * @author gcollada
 *
 */
@RestController
@RequestMapping(value = "/fields")
public class FieldController {
	
	/**
	 * Servicio para realizar operaciones lógicas sobre Fields
	 */
	@Autowired
	FieldService fieldService;
	
	/**
	 * Método que procesa la petición para registrar un nuevo Field en el sistema
	 * @param newField Modelo JSON del nuevo Field a registrar (tipo NewField)
	 * @return respuesta HTTP con el modelo JSON ya creado del Field (tipo FieldModel)
	 * y un código de respuesta 201 (CREATED)
	 */
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<FieldModel> registerField(@Validated @RequestBody NewField newField ) {
		Field campo = fieldService.create(newField);
		FieldModel model = fieldService.add(campo);
		return new ResponseEntity<FieldModel>(model, HttpStatus.CREATED);			
	}
	
	/**
	 * Método que procesa la petición para modificar un Field del sistema
	 * @param code Código del Field que se desea modificar
	 * @param newField Modelo JSON con la información que tendrá el Field modificado
	 * @return respuesta HTTP con el modelo JSON modificado del Field (tipo FieldModel)
	 * y un código de respuesta 200 (OK)
	 */
	@RequestMapping(value = "/{code}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<FieldModel> updateField(@PathVariable String code, @Validated @RequestBody NewField newField ) {
		Field campo = fieldService.create(newField);
		FieldModel model = fieldService.update(code, campo);
		return new ResponseEntity<FieldModel>(model, HttpStatus.OK);
	}
	
	/**
	 * Método que procesa la petición para recuperar un Field por su código.
	 * @param code Código del Field que se desea recuperar
	 * @return respuesta HTTP con el modelo JSON del Field (tipo FieldModel)
	 * y un código de respuesta 200 (OK)
	 */
	@RequestMapping(value = "/{code}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<FieldModel> getFieldByCode(@PathVariable String code) {
		FieldModel model = FieldAdapter.getFieldModel(fieldService.getFieldByCode(code));
		return new ResponseEntity<FieldModel>(model, HttpStatus.OK);
	}
	
	/**
	 * Método que procesa la petición para listar todos los Fields del sistema
	 * @return respuesta HTTP con una lista de los modelos JSON de los Field recuperados (tipo List<FieldModel>)
	 * y un código de respuesta 200 (OK)
	 */
	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FieldModel>> listFields(){
		List<FieldModel> listModels = fieldService.getFields();
		return new ResponseEntity<List<FieldModel>>(listModels, HttpStatus.OK);
    }
	
	/**
	 * Método que procesa la petición para borrar un Field
	 * @param code Código del Field que se desea borrar
	 * @return respuesta HTTP con un código 204 (NO CONTENT)
	 */
	@RequestMapping(value = "/{code}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<FieldModel> deleteField(@PathVariable String code){
		fieldService.delete(code);
		return new ResponseEntity<FieldModel>(HttpStatus.NO_CONTENT);
	}
}
