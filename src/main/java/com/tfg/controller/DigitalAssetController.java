package com.tfg.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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

import com.tfg.model.DigitalAsset;
import com.tfg.services.DigitalAssetService;

@RestController
public class DigitalAssetController {

	@Autowired
	DigitalAssetService service;
	
	@RequestMapping(value = "/asset/add", method = RequestMethod.POST, consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<String> registerDigitalAsset(@RequestBody Map<String, Object> payload ) throws ParseException {
		DigitalAsset asset = new DigitalAsset(payload.get( "codigo" ).toString(),payload.get( "descripcion" ).toString(),
				payload.get( "tipo" ).toString(), payload.get( "entidad" ).toString(), payload.get( "contacto" ).toString(),
				payload.get( "autor" ).toString(), (double) payload.get( "tamano" ), payload.get( "unidad_tamano" ).toString(), 
				payload.get( "path" ).toString(), new SimpleDateFormat("dd/MM/yyyy").parse(payload.get( "fecha_creacion" ).toString()),
				new SimpleDateFormat("dd/MM/yyyy").parse(payload.get( "fecha_modificacion" ).toString()));
		asset.setCreateUser("gonzi");
		asset.setCreateDate(new Date());
		
		service.add(asset);

		System.out.print("Digital Asset registrado: " + new JSONObject( payload ).toString());
		
		return new ResponseEntity<String>( "{\"response\":\"Digital Asset registrado\"}",
				HttpStatus.CREATED );
	}
	
	@RequestMapping("/asset/getDigitalAssetsByFilters")
    public List<DigitalAsset> getDigitalAssetsByFilters(@RequestParam Map<String,Object> allRequestParams ) {
		return service.getDigitalAssetsByFilters(allRequestParams);
    }
	
	@RequestMapping("/asset/getDigitalAssets")
    public List<DigitalAsset> getDigitalAssets() {
		return service.getDigitalAssets();
    }
}
