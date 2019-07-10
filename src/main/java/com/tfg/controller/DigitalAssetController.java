package com.tfg.controller;

import java.util.List;
import java.util.Map;
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

import com.tfg.model.DigitalAsset;
import com.tfg.services.DigitalAssetService;
import com.tfg.services.model.AssetModel;
import com.tfg.services.model.GroupFieldModel;
import com.tfg.services.model.NewAsset;
/**
 * Clase encargada de procesar las peticiones relacionadas con el recurso de Digital Assets.
 * @author gcollada
 *
 */
@RestController
@RequestMapping(value = "/assets")
public class DigitalAssetController {

	/**
	 * Servicio para realizar operaciones lógicas sobre Digital Assets
	 */
	@Autowired
	DigitalAssetService assetService;	
	
	/**
	 * Método que procesa la petición para registrar un nuevo Digital Asset en el sistema
	 * @param newDigitalAsset Modelo JSON del nuevo Digital Asset a registrar (tipo NewAsset)
	 * @return respuesta HTTP con el modelo JSON ya creado del Digital Asset (tipo AssetModel)
	 * y un código de respuesta 201 (CREATED)
	 */
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AssetModel> registerDigitalAsset(@Validated @RequestBody NewAsset newDigitalAsset){
		DigitalAsset asset = assetService.create(newDigitalAsset);
		assetService.add(newDigitalAsset, asset);
		AssetModel model = assetService.findByCodigo(asset.getCodigo());
		return new ResponseEntity<AssetModel>(model, HttpStatus.CREATED);	
	}
	
	/**
	 * Método que procesa la petición para buscar Digital Assets ya registrados
	 * @param allRequestParams un mapa de campos/valor, correspondientes a los filtros por los
	 * que se desean filtrar los Digital Assets. No es obligatorio
	 * @return respuesta HTTP con una lista de los modelos JSON de los Digital Assets recuperados (tipo List<AssetModel>)
	 * y un código de respuesta 200 (OK)
	 */
	@RequestMapping(value = "", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AssetModel>> getDigitalAssets(@RequestParam(required=false) Map<String,Object> allRequestParams ) {
		List<AssetModel> result = assetService.getDigitalAssetsByFilters(allRequestParams);
		return new ResponseEntity<List<AssetModel>>(result, HttpStatus.OK);
    }
	
	/**
	 * Método que procesa la petición para borrar un Digital Asset
	 * @param code Código del Digital Asset que se desea borrar
	 * @return respuesta HTTP con un código 204 (NO CONTENT)
	 */
	@RequestMapping(value = "/{code}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AssetModel> deleteDigitalAsset(@PathVariable String code){
		assetService.delete(code);
		return new ResponseEntity<AssetModel>(HttpStatus.NO_CONTENT);		
	}
	
	/**
	 * Método que procesa la petición para añadir metadatos a un Digital Asset
	 * @param models Lista de modelos JSON con las agrupaciones de metadatos a añadir (tipo List<GroupFieldModel>)
	 * @param code Código del Digital Asset al que se desean asociar los metadatos
	 * @return respuesta HTTP con el modelo JSON del Digital Asset (tipo AssetModel)
	 * y un código de respuesta 201 (CREATED)
	 */
	@RequestMapping(value = "/addMetadata/{code}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AssetModel> addMetadataToDigitalAsset(@RequestBody List<GroupFieldModel> models, 
			@PathVariable String code) {
		assetService.addMetadata(models, code);
		AssetModel model = assetService.findByCodigo(code);
		return new ResponseEntity<AssetModel>(model, HttpStatus.CREATED);
	}
	
	/**
	 * Método que procesa la petición para desasociar metadatos a un Digital Asset
	 * @param models Lista de modelos JSON con las agrupaciones de metadatos a eliminar (tipo List<GroupFieldModel>)
	 * @param code Código del Digital Asset al que se desean eliminar los metadatos
	 * @return respuesta HTTP con el modelo JSON del Digital Asset (tipo AssetModel)
	 * y un código de respuesta 200 (OK)
	 */
	@RequestMapping(value = "/deleteMetadata/{code}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AssetModel> deleteMetadataToDigitalAsset(@RequestBody List<GroupFieldModel> models, 
			@PathVariable String code) {
		assetService.deleteMetadata(models, code);
		AssetModel model = assetService.findByCodigo(code);
		return new ResponseEntity<AssetModel>(model, HttpStatus.OK);
	}
	
}
