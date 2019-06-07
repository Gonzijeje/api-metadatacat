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
import org.springframework.web.bind.annotation.RestController;

import com.tfg.esearch.Search;
import com.tfg.model.Ac_Asset;
import com.tfg.model.DigitalAsset;
import com.tfg.model.Grupo;
import com.tfg.model.Grupo_campo;
import com.tfg.services.Ac_AssetService;
import com.tfg.services.CampoService;
import com.tfg.services.DigitalAssetService;
import com.tfg.services.GrupoService;
import com.tfg.services.Grupo_campoService;
import com.tfg.services.Valor_CampoService;

@RestController
public class DigitalAssetController {

	@Autowired
	DigitalAssetService service;
	
	@Autowired
	CampoService campoService;
	
	@Autowired
	GrupoService grupoService;
	
	@Autowired
	Valor_CampoService valorService;
	
	@Autowired
	Grupo_campoService grupoCampoService;
	
	@Autowired
	Ac_AssetService acAssetService;
	
	@Autowired
	Search esSearch;
	
	@RequestMapping(value = "/asset/add", method = RequestMethod.POST, consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<String> registerDigitalAsset(@RequestBody Map<String, Object> payload) throws ParseException {
		DigitalAsset asset = service.create(payload);
		int way = service.add(asset);
		if(way==0) {
			List<Grupo_campo> grcampos = new ArrayList<Grupo_campo>();
			List<Ac_Asset> asociaciones = new ArrayList<Ac_Asset>();
			campoService.addListCampos(new ArrayList<String>(payload.keySet()));
			grupoService.add(new Grupo("Grupo básicos", "Grupo campos básicos","gonzi",new Date()));
			valorService.addListValores(new ArrayList<Object>(payload.values()));
			Grupo basico = grupoService.getGrupoByCodigo("Grupo básicos");
			payload.forEach((k,v)-> {				
				grcampos.add(new Grupo_campo(basico,campoService.getCampoByCodigo(k),valorService.getValor(v)));
				asociaciones.add(new Ac_Asset(asset,basico,campoService.getCampoByCodigo(k)));			
			});	
			grupoCampoService.addListGrupo_Campo(grcampos);
			acAssetService.addListAc_Asset(asociaciones);
			System.out.print("Digital Asset registrado: " + new JSONObject( payload ).toString());
			
			return new ResponseEntity<String>( "{\"response\":\"Digital Asset registrado\"}",
					HttpStatus.CREATED );
			//Crear doc en ElasticSearch
		}else if(way==1){
			return new ResponseEntity<String>( "{\"response\":\"Código de DigitalAsset ya existe\"}",
					HttpStatus.BAD_REQUEST );
		}else {
			return new ResponseEntity<String>( "{\"response\":\"Path de DigitalAsset ya existe\"}",
					HttpStatus.BAD_REQUEST );
		}	
	}
	
	@RequestMapping("/asset/getDigitalAssetsByFilters")
    public List<DigitalAsset> getDigitalAssetsByFilters(@RequestParam Map<String,Object> allRequestParams ) {
		return service.getDigitalAssetsByFilters(allRequestParams);
    }
	
	@RequestMapping("/asset/getDigitalAssets")
    public List<DigitalAsset> getDigitalAssets(){
		esSearch.matchAll();
		return service.getDigitalAssets();
    }
	
	@RequestMapping(value = "/asset/delete", method = RequestMethod.DELETE, consumes = "application/json")
	public ResponseEntity<String> deleteDigitalAsset(@RequestParam String codigo){
		if(service.delete(codigo)) {
			return new ResponseEntity<String>( "{\"response\":\"Digital Asset eliminado\"}",
					HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("{\"response\":\"Código de DigitalAsset no existe\"}",
					HttpStatus.BAD_REQUEST);
		}		
	}
	
	@RequestMapping(value = "/asset/addMetadata", method = RequestMethod.POST, consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<String> addMetadataToDigitalAsset(@RequestBody Map<String,Map<String,Object>> payload, 
			@RequestParam String codigo) throws ParseException {
		for(String key:payload.keySet()) {
			Map<String,Object> mappa = payload.get(key);
			//Grupo
			grupoService.add(new Grupo(key.toString(),null,"gonzi",new Date()));
			campoService.addListCampos(new ArrayList<String>(mappa.keySet()));
			valorService.addListValores(new ArrayList<Object>(mappa.values()));
			//Grupo_campo
			mappa.forEach((k,v)-> {
				grupoCampoService.add(new Grupo_campo(grupoService.getGrupoByCodigo(key),campoService.getCampoByCodigo(k),valorService.getValor(v)));
				acAssetService.add(new Ac_Asset(service.findByCodigo(codigo),grupoService.getGrupoByCodigo(key),campoService.getCampoByCodigo(k)));
			});
		}
		return new ResponseEntity<String>( "{\"response\":\"Metadato añadido al Digital Asset\"}",
				HttpStatus.OK );
	}
	
	@RequestMapping(value = "/asset/modifyMetadata", method = RequestMethod.POST, consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<String> modifyMetadataOfDigitalAsset(@RequestBody Map<String,Map<String,Object>> payload, 
			@RequestParam String codigo) throws ParseException {
		DigitalAsset asset = service.findByCodigo(codigo);
		List<String> listaGrupos = new ArrayList<String>(payload.keySet());
		if(asset!=null) {
			if(grupoService.checkListGrupos(listaGrupos)) {
				for(String key:payload.keySet()) {
					Map<String,Object> mappa = payload.get(key);
					grupoService.add(grupoService.getGrupoByCodigo(key));
					List<String> listCampos = new ArrayList<String>(mappa.keySet());
					campoService.addListCampos(listCampos);
					List<Object> valores = new ArrayList<Object>(payload.values());
					valorService.addListValores(valores);
					//Grupo_campo
					mappa.forEach((k,v)-> {grupoCampoService.add(new Grupo_campo(grupoService.getGrupoByCodigo(key),campoService.getCampoByCodigo(k),valorService.getValor(v)));
					acAssetService.add(new Ac_Asset(service.findByCodigo(codigo),grupoService.getGrupoByCodigo(key),campoService.getCampoByCodigo(k)));});
					//Ac_Asset
				}
				return new ResponseEntity<String>( "{\"response\":\"Metadato añadido al Digital Asset\"}",
						HttpStatus.OK );
			}else {
				return new ResponseEntity<String>("{\"response\":\"Metadatos a modificar no existen\"}",
						HttpStatus.BAD_REQUEST);
			}
		}
		return new ResponseEntity<String>("{\"response\":\"Código de DigitalAsset no existe\"}",
				HttpStatus.BAD_REQUEST);
	}
	
}
