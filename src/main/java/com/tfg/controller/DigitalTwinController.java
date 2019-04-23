package com.tfg.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.tfg.model.Ac_Asset;
import com.tfg.model.DigitalAsset;
import com.tfg.model.Grupo;
import com.tfg.model.Grupo_campo;
import com.tfg.services.Ac_AssetService;
import com.tfg.services.Ac_TwinService;
import com.tfg.services.CampoService;
import com.tfg.services.DigitalAssetService;
import com.tfg.services.GrupoService;
import com.tfg.services.Grupo_campoService;
import com.tfg.services.Valor_CampoService;

public class DigitalTwinController {
	
	@Autowired
	DigitalTwinService service;
	
	@Autowired
	CampoService campoService;
	
	@Autowired
	GrupoService grupoService;
	
	@Autowired
	Valor_CampoService valorService;
	
	@Autowired
	Grupo_campoService grupoCampoService;
	
	@Autowired
	Ac_TwinService acTwinService;
	
	@RequestMapping(value = "/twin/add", method = RequestMethod.POST, consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<String> registerDigitalTwin(@RequestBody Map<String, Object> payload) throws ParseException {
		DigitalAsset asset = new DigitalAsset(payload.get( "codigo" ).toString(),payload.get( "descripcion" ).toString(),
				payload.get( "tipo" ).toString(), payload.get( "entidad" ).toString(), payload.get( "contacto" ).toString(),
				payload.get( "autor" ).toString(),Double.parseDouble((String) payload.get( "tamano" )), payload.get( "unidad_tamano" ).toString(), 
				payload.get( "path" ).toString(), new SimpleDateFormat("dd/MM/yyyy").parse(payload.get( "fecha_creacion" ).toString()),
				new SimpleDateFormat("dd/MM/yyyy").parse(payload.get( "fecha_modificacion" ).toString()));
		asset.setCreateUser("gonzi");
		asset.setCreateDate(new Date());		
		service.add(asset);
		//Registrar campos
		List<String> listCampos = new ArrayList<String>(payload.keySet());
		campoService.addListCampos(listCampos);
		//Registrar grupo
		grupoService.add(new Grupo("Grupo básicos", "Grupo campos básicos","gonzi",new Date()));
		//Registrar valores
		List<Object> valores = new ArrayList<Object>(payload.values());
		valorService.addListValores(valores);
		//Asociar campos y grupos
		//Usar el metodo saveALL pasandole una lista creada antes
		Grupo basico = grupoService.getGrupoByCodigo("Grupo básicos");
		payload.forEach((k,v)-> grupoCampoService.add(new Grupo_campo(basico,campoService.getCampoByCodigo(k),valorService.getValor(v))));		
		//Asociar digital asset y grupos_campos
		List<Ac_Asset> asociaciones = new ArrayList<Ac_Asset>();
		payload.forEach((k,v)-> {asociaciones.add(new Ac_Asset(asset,basico,campoService.getCampoByCodigo(k)));});
		acTwinService.addListAc_Asset(asociaciones);
		//
		System.out.print("Digital Asset registrado: " + new JSONObject( payload ).toString());
		
		return new ResponseEntity<String>( "{\"response\":\"Digital Asset registrado\"}",
				HttpStatus.CREATED );
	}

}
