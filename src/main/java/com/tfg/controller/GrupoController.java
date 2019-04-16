package com.tfg.controller;

import java.util.Date;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tfg.model.Grupo;
import com.tfg.services.GrupoService;

/**
 * 
 * @author gcollada
 *
 */
@RestController
public class GrupoController {
	
	@Autowired
	GrupoService service;
	
	@RequestMapping(value = "/grupo/add", method = RequestMethod.POST, consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<String> registerGrupo(@RequestBody Map<String, Object> payload ) {
		Grupo grupo = new Grupo( payload.get( "codigo" ).toString(),
				payload.get( "descripcion" ).toString());
		grupo.setCreateUser("gonzi");
		grupo.setCreateDate(new Date());
		
		service.add(grupo);

		System.out.print("Grupo registrado: " + new JSONObject( payload ).toString());
		
		return new ResponseEntity<String>( "{\"response\":\"Grupo registrado\"}",
				HttpStatus.CREATED );
	}
	
	@RequestMapping(value = "/grupo/delete", method = RequestMethod.DELETE, consumes = "application/json")
	public ResponseEntity<String> deleteGrupo(@RequestParam String codigo){
		service.delete(codigo);
		return new ResponseEntity<String>("{\"response\":\"Grupo eliminado\"}",
				HttpStatus.OK);
	}

}
