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
		Grupo grupo = service.create(payload);
		if(grupo!=null) {
			if(service.add(grupo)) {
				return new ResponseEntity<String>( "{\"response\":\"Grupo registrado\"}",
						HttpStatus.CREATED );
			}else {
				return new ResponseEntity<String>( "{\"response\":\"Código de grupo ya existe\"}",
						HttpStatus.CONFLICT);
			}
		}
		return new ResponseEntity<String>( "{\"response\":\"Datos introducidos incorrectos\"}",
				HttpStatus.BAD_REQUEST);				
	}
	
	@RequestMapping("/grupo")
	public Object getGrupo(@RequestParam String codigo) {
		Grupo grupo = service.getGrupoByCodigo(codigo);
		if(grupo!=null) {
			return grupo;
		}
		return new ResponseEntity<String>( "{\"response\":\"Código de grupo no existe\"}",
					HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping("/grupo/list")
    public List<Grupo> listGrupos(){
		return service.getGrupos();
    }

	@RequestMapping(value = "/grupo/delete", method = RequestMethod.DELETE, consumes = "application/json")
	public ResponseEntity<String> deleteGrupo(@RequestParam String codigo){
		if(service.delete(codigo)) {
			return new ResponseEntity<String>("{\"response\":\"Grupo eliminado\"}",
					HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("{\"response\":\"Código de grupo no existe\"}",
					HttpStatus.NOT_FOUND);
		}
	}
}