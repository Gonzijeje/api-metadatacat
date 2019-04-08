package com.tfg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tfg.model.Campo;
import com.tfg.model.Grupo;
import com.tfg.model.Grupo_campo;
import com.tfg.services.CampoService;
import com.tfg.services.GrupoService;
import com.tfg.services.Grupo_campoService;

/**
 * 
 * @author gcollada
 *
 */
@RestController
public class Grupo_campoController {

	@Autowired
	Grupo_campoService service;
	
	@Autowired
	GrupoService grupoService;
	
	@Autowired
	CampoService campoService;
	
	@RequestMapping(value = "/grupoCampo/add", method = RequestMethod.GET)
	public ResponseEntity<String> registerMetadata(@RequestParam(value="grupo") String grupo, @RequestParam(value="campo") String campo,
			@RequestParam(value="valor") String valor) {
		Grupo gr = grupoService.getGrupoByCodigo(grupo);
		Campo ca = campoService.getMetadatoByCodigo(campo);
		Grupo_campo gc = new Grupo_campo(gr,ca,valor);
		
		service.add(gc);

		//System.out.print("Grupo registrado: " + new JSONObject( gc ).toString());
		
		return new ResponseEntity<String>( "{\"response\":\"Grupo registrado\"}",
				HttpStatus.CREATED );
	}
	
}