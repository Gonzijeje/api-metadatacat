package com.tfg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tfg.model.Field;
import com.tfg.model.Group;
import com.tfg.model.GroupField;
import com.tfg.model.Value;
import com.tfg.services.FieldService;
import com.tfg.services.GroupService;
import com.tfg.services.GroupFieldService;
import com.tfg.services.Valor_CampoService;

/**
 * 
 * @author gcollada
 *
 */
@RestController
public class Grupo_campoController {

	@Autowired
	GroupFieldService service;
	
	@Autowired
	GroupService grupoService;
	
	@Autowired
	FieldService campoService;
	
	@Autowired
	Valor_CampoService valorService;
	
	@RequestMapping(value = "/grupoCampo/add", method = RequestMethod.GET)
	public ResponseEntity<String> registerMetadata(@RequestParam(value="grupo") String grupo, @RequestParam(value="campo") String campo,
			@RequestParam(value="valor") String valor) {
		Group gr = grupoService.getGrupoByCodigo(grupo);
		Field ca = campoService.getCampoByCodigo(campo);
		Value va = valorService.getValor(valor);
		GroupField gc = new GroupField(gr,ca,va);
		
		service.add(gc);

	 	//System.out.print("Grupo registrado: " + new JSONObject( gc ).toString());
		
		return new ResponseEntity<String>( "{\"response\":\"Grupo registrado\"}",
				HttpStatus.CREATED );
	}
	
}
