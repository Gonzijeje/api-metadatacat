package com.tfg.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tfg.esearch.Search;
import com.tfg.model.Ac_Asset;
import com.tfg.model.DigitalAsset;
import com.tfg.model.Group;
import com.tfg.model.GroupField;
import com.tfg.pojos.AssetModel;
import com.tfg.pojos.NewAsset;
import com.tfg.services.Ac_AssetService;
import com.tfg.services.FieldService;
import com.tfg.services.DigitalAssetService;
import com.tfg.services.GroupService;
import com.tfg.services.Grupo_campoService;
import com.tfg.services.Valor_CampoService;

@RestController
@RequestMapping(value = "/assets")
public class DigitalAssetController {

	@Autowired
	DigitalAssetService assetService;
	
	@Autowired
	FieldService fieldService;
	
	@Autowired
	GroupService groupService;
	
	@Autowired
	Valor_CampoService valorService;
	
	@Autowired
	Grupo_campoService grupoCampoService;
	
	@Autowired
	Ac_AssetService acAssetService;
	
	@Autowired
	Search esSearch;
	
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AssetModel> registerDigitalAsset(@Validated @RequestBody NewAsset newDigitalAsset) throws ParseException {
		DigitalAsset asset = assetService.create(newDigitalAsset);
		AssetModel model = assetService.add(newDigitalAsset, asset);
		return new ResponseEntity<AssetModel>(model, HttpStatus.CREATED);	
	}
	
	@RequestMapping("/asset/getDigitalAssetsByFilters")
    public List<DigitalAsset> getDigitalAssetsByFilters(@RequestParam Map<String,Object> allRequestParams ) {
		return assetService.getDigitalAssetsByFilters(allRequestParams);
    }
	
	@RequestMapping("/asset/getDigitalAssets")
    public List<DigitalAsset> getDigitalAssets(){
		esSearch.matchAll();
		return assetService.getDigitalAssets();
    }
	
	@RequestMapping(value = "/asset/delete", method = RequestMethod.DELETE, consumes = "application/json")
	public ResponseEntity<String> deleteDigitalAsset(@RequestParam String codigo){
		if(assetService.delete(codigo)) {
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
			groupService.add(new Group(key.toString(),null));
			fieldService.addListCampos(new ArrayList<String>(mappa.keySet()));
			valorService.addListValores(new ArrayList<Object>(mappa.values()));
			//Grupo_campo
			mappa.forEach((k,v)-> {
				grupoCampoService.add(new GroupField(groupService.getGrupoByCodigo(key),fieldService.getCampoByCodigo(k),valorService.getValor(v)));
				acAssetService.add(new Ac_Asset(assetService.findByCodigo(codigo),groupService.getGrupoByCodigo(key),fieldService.getCampoByCodigo(k)));
			});
		}
		return new ResponseEntity<String>( "{\"response\":\"Metadato añadido al Digital Asset\"}",
				HttpStatus.OK );
	}
	
	@RequestMapping(value = "/asset/modifyMetadata", method = RequestMethod.POST, consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<String> modifyMetadataOfDigitalAsset(@RequestBody Map<String,Map<String,Object>> payload, 
			@RequestParam String codigo) throws ParseException {
		DigitalAsset asset = assetService.findByCodigo(codigo);
		List<String> listaGrupos = new ArrayList<String>(payload.keySet());
		if(asset!=null) {
			if(groupService.checkListGrupos(listaGrupos)) {
				for(String key:payload.keySet()) {
					Map<String,Object> mappa = payload.get(key);
					groupService.add(groupService.getGrupoByCodigo(key));
					List<String> listCampos = new ArrayList<String>(mappa.keySet());
					fieldService.addListCampos(listCampos);
					List<Object> valores = new ArrayList<Object>(payload.values());
					valorService.addListValores(valores);
					//Grupo_campo
					mappa.forEach((k,v)-> {grupoCampoService.add(new GroupField(groupService.getGrupoByCodigo(key),fieldService.getCampoByCodigo(k),valorService.getValor(v)));
					acAssetService.add(new Ac_Asset(assetService.findByCodigo(codigo),groupService.getGrupoByCodigo(key),fieldService.getCampoByCodigo(k)));});
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
