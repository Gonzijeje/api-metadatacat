package com.tfg.controller;

import java.text.ParseException;
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
import org.springframework.web.bind.annotation.RequestParam;

import com.tfg.model.Ac_Twin;
import com.tfg.model.DigitalAsset;
import com.tfg.model.DigitalTwin;
import com.tfg.model.Grupo;
import com.tfg.model.Grupo_campo;
import com.tfg.services.Ac_TwinService;
import com.tfg.services.CampoService;
import com.tfg.services.DigitalTwinService;
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
		DigitalTwin twin = service.create(payload);
		int way = service.add(twin);
		if(way==0) {
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
			List<Ac_Twin> asociaciones = new ArrayList<Ac_Twin>();
			payload.forEach((k,v)-> {asociaciones.add(new Ac_Twin(twin,basico,campoService.getCampoByCodigo(k)));});
			acTwinService.addListAc_Twin(asociaciones);
			//
			System.out.print("Digital Twin registrado: " + new JSONObject( payload ).toString());
			
			return new ResponseEntity<String>( "{\"response\":\"Digital Twin registrado\"}",
					HttpStatus.CREATED );
		}else if(way==1){
			return new ResponseEntity<String>( "{\"response\":\"Código de DigitalTwin ya existe\"}",
					HttpStatus.BAD_REQUEST );
		}else {
			return new ResponseEntity<String>( "{\"response\":\"Path de DigitalTwin ya existe\"}",
					HttpStatus.BAD_REQUEST );
		}	
	}
	
	@RequestMapping("/twin/getDigitalTwinsByFilters")
    public List<DigitalTwin> getDigitalAssetsByFilters(@RequestParam Map<String,Object> allRequestParams ) {
		return service.getDigitalTwinsByFilters(allRequestParams);
    }
	
	@RequestMapping("/twin/getDigitalTwins")
    public List<DigitalTwin> getDigitalAssets(){
		//esSearch.matchAll();
		return service.getDigitalTwins();
    }
	
	@RequestMapping(value = "/twin/delete", method = RequestMethod.DELETE, consumes = "application/json")
	public ResponseEntity<String> deleteDigitalTwin(@RequestParam String codigo){
		service.delete(codigo);
		return new ResponseEntity<String>( "{\"response\":\"Digital Twin eliminado\"}",
				HttpStatus.OK );
	}

}
