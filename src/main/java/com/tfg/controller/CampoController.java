package com.tfg.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tfg.model.Campo;
import com.tfg.services.CampoService;

/**
 * 
 * @author gcollada
 *
 */
@RestController
public class CampoController {
	
	@Autowired
	CampoService service;
	
	@RequestMapping(value = "/campo/add", method = RequestMethod.POST, consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<String> registerCampo(@RequestBody Map<String, Object> payload ) {
		Campo campo = service.create(payload);
		if(campo!=null) {
			if(service.add(campo)) {
				return new ResponseEntity<String>("{\"response\":\"Campo registrado\"}",
						HttpStatus.CREATED );
			}else {
				return new ResponseEntity<String>( "{\"response\":\"Código de campo ya existe\"}",
						HttpStatus.CONFLICT);
			}
		}
		return new ResponseEntity<String>( "{\"response\":\"Datos introducidos incorrectos\"}",
				HttpStatus.BAD_REQUEST);			
	}
	
	@RequestMapping("/campo")
	public Object getCampo(@RequestParam String codigo) {
		Campo campo = service.getCampoByCodigo(codigo);
		if(campo!=null) {
			return campo;
		}
		return new ResponseEntity<String>( "{\"response\":\"Código de campo no existe\"}",
					HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping("/campo/list")
    public List<Campo> listCampos(){
		return service.getCampos();
    }
	
	@RequestMapping(value = "/campo/delete", method = RequestMethod.DELETE, consumes = "application/json")
	public ResponseEntity<String> deleteCampo(@RequestParam String codigo){
		if(service.delete(codigo)) {
			return new ResponseEntity<String>("{\"response\":\"Campo eliminado\"}",
					HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("{\"response\":\"Código de campo no existe\"}",
					HttpStatus.NOT_FOUND);
		}	
	}
}
