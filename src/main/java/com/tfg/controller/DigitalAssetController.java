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
import org.springframework.web.bind.annotation.PathVariable;
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
import com.tfg.pojos.FieldModel;
import com.tfg.pojos.GroupFieldModel;
import com.tfg.pojos.NewAsset;
import com.tfg.services.Ac_AssetService;
import com.tfg.services.FieldService;
import com.tfg.services.DigitalAssetService;
import com.tfg.services.GroupService;
import com.tfg.services.GroupFieldService;
import com.tfg.services.ValueService;

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
	ValueService valorService;
	
	@Autowired
	GroupFieldService grupoCampoService;
	
	@Autowired
	Ac_AssetService acAssetService;
	
	@Autowired
	Search esSearch;
	
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AssetModel> registerDigitalAsset(@Validated @RequestBody NewAsset newDigitalAsset) throws ParseException {
		DigitalAsset asset = assetService.create(newDigitalAsset);
		assetService.add(newDigitalAsset, asset);
		AssetModel model = assetService.findByCodigo(asset.getCodigo());
		return new ResponseEntity<AssetModel>(model, HttpStatus.CREATED);	
	}
	
	@RequestMapping(value = "/getDigitalAssets", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AssetModel>> getDigitalAssets(@RequestParam(required=false) Map<String,Object> allRequestParams ) {
		List<AssetModel> result = assetService.getDigitalAssetsByFilters(allRequestParams);
		return new ResponseEntity<List<AssetModel>>(result, HttpStatus.OK);
    }
	
	
	@RequestMapping(value = "/{code}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AssetModel> deleteDigitalAsset(@PathVariable String code){
		assetService.delete(code);
		return new ResponseEntity<AssetModel>(HttpStatus.ACCEPTED);		
	}
	
	@RequestMapping(value = "/addMetadata/{code}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AssetModel> addMetadataToDigitalAsset(@RequestBody List<GroupFieldModel> models, 
			@PathVariable String code) {
		assetService.addMetadata(models, code);
		AssetModel model = assetService.findByCodigo(code);
		return new ResponseEntity<AssetModel>(model, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/deleteMetadata/{code}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AssetModel> deleteMetadataToDigitalAsset(@RequestBody List<GroupFieldModel> models, 
			@PathVariable String code) {
		assetService.deleteMetadata(models, code);
		AssetModel model = assetService.findByCodigo(code);
		return new ResponseEntity<AssetModel>(model, HttpStatus.OK);
	}
	
}
