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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	
	@RequestMapping(value = "/asset/add", method = RequestMethod.POST, consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<String> registerDigitalAsset(@RequestBody Map<String, Object> payload) throws ParseException {
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
		acAssetService.addListAc_Asset(asociaciones);
		//
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
	
	@RequestMapping(value = "/asset/delete", method = RequestMethod.DELETE, consumes = "application/json")
	public ResponseEntity<String> deleteDigitalAsset(@RequestParam String codigo){
		service.delete(codigo);
		return new ResponseEntity<String>( "{\"response\":\"Digital Asset eliminado\"}",
				HttpStatus.CREATED );
	}
	
	@RequestMapping(value = "/asset/addMetadata", method = RequestMethod.POST, consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<String> addMetadataToDigitalAsset(@RequestBody Map<String,Map<String,Object>> payload) throws ParseException {
		for(String key:payload.keySet()) {
			Map<String,Object> mappa = payload.get(key);
			//Campos
			List<String> listCampos = new ArrayList<String>(mappa.keySet());
			campoService.addListCampos(listCampos);
			//Valores
			List<Object> valores = new ArrayList<Object>(payload.values());
			valorService.addListValores(valores);
			mappa.forEach((k,v)-> grupoCampoService.add(new Grupo_campo(basico,campoService.getCampoByCodigo(k),valorService.getValor(v))));
			
			Field field = fieldService.findByName(json.get("field_name").toString());
			if (field == null) {
				fieldService.add(new Field(json.get("field_name").toString(), "Default description"));
				field = fieldService.findByName(json.get("field_name").toString());
			} 
			Group group = gService.findByGroupName(json.get("group_name").toString());
			if (group == null) {// si no existe, crearlo
				gService.createGroup(new Group(json.get("group_name").toString(), "Default description"));
				group = gService.findByGroupName(json.get("group_name").toString());
			} 
			if (!vService.existisByValue(json.get("value").toString())) {
				vService.add(new Value(json.get("value").toString()));
			}
			Value value = vService.findByValue(json.get("value").toString()); 
			FieldAssociation fa = new FieldAssociation(field.getId(), group.getGroup_id(), value.getId());
			faService.create(fa);

			FAProperty fap = new FAProperty(property.getId_property(), field.getId(), group.getGroup_id());
			fapService.create(fap);
		}


		return new ResponseEntity<String>( "{\"response\":\"Digital Asset registrado\"}",
				HttpStatus.CREATED );
	}
	
}
