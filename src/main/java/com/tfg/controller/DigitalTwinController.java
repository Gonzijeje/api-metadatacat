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

import com.tfg.model.DigitalTwin;
import com.tfg.services.DigitalTwinService;
import com.tfg.services.model.GroupFieldModel;
import com.tfg.services.model.NewTwin;
import com.tfg.services.model.TwinModel;
/**
 * Clase encargada de procesar las peticiones relacionadas con el recurso de Digital Twins.
 * @author gcollada
 *
 */
@RestController
@RequestMapping(value = "/twins")
public class DigitalTwinController {

	/**
	 * Servicio para realizar operaciones lógicas sobre Digital Twins
	 */
	@Autowired
	DigitalTwinService twinService;
	
	/**
	 * Método que procesa la petición para registrar un nuevo Digital Twin en el sistema
	 * @param newDigitalTwin Modelo JSON del nuevo Digital Twin a registrar (tipo NewTwin)
	 * @return respuesta HTTP con el modelo JSON ya creado del Digital Twin (tipo TwinModel)
	 * y un código de respuesta 201 (CREATED)
	 */
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TwinModel> registerDigitalTwin(@Validated @RequestBody NewTwin newDigitalTwin){
		DigitalTwin twin = twinService.create(newDigitalTwin);
		twinService.add(newDigitalTwin, twin);
		TwinModel model = twinService.findByCodigo(twin.getCodigo());
		return new ResponseEntity<TwinModel>(model, HttpStatus.CREATED);
	}
	
	/**
	 * Método que procesa la petición para buscar Digital Twins ya registrados
	 * @param allRequestParams un mapa de campos/valor, correspondientes a los filtros por los
	 * que se desean filtrar los Digital Assets. No es obligatorio
	 * @return respuesta HTTP con una lista de los modelos JSON de los Digital Twins recuperados (tipo List<TwinModel>)
	 * y un código de respuesta 200 (OK)
	 */
	@RequestMapping(value = "", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TwinModel>> getDigitalAssets(@RequestParam(required=false) Map<String,Object> allRequestParams){
		List<TwinModel> result = twinService.getDigitalTwinsByFilters(allRequestParams);
		return new ResponseEntity<List<TwinModel>>(result, HttpStatus.OK);
    }
	
	/**
	 * Método que procesa la petición para borrar un Digital Twin
	 * @param code Código del Digital Twin que se desea borrar
	 * @return respuesta HTTP con un código 204 (NO CONTENT)
	 */
	@RequestMapping(value = "{code}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TwinModel> deleteDigitalTwin(@PathVariable String code){
		twinService.delete(code);
		return new ResponseEntity<TwinModel>(HttpStatus.NO_CONTENT);
	}
	
	/**
	 * Método que procesa la petición para añadir metadatos a un Digital Twin
	 * @param models Lista de modelos JSON con las agrupaciones de metadatos a añadir (tipo List<GroupFieldModel>)
	 * @param code Código del Digital Twin al que se desean asociar los metadatos
	 * @return respuesta HTTP con el modelo JSON del Digital Twin (tipo TwinModel)
	 * y un código de respuesta 201 (CREATED)
	 */
	@RequestMapping(value = "/addMetadata/{code}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TwinModel> addMetadataToDigitalTwin(@RequestBody List<GroupFieldModel> models, 
			@PathVariable String code) {
		twinService.addMetadata(models, code);
		TwinModel model = twinService.findByCodigo(code);
		return new ResponseEntity<TwinModel>(model, HttpStatus.CREATED);
	}
	
	/**
	 * Método que procesa la petición para desasociar metadatos a un Digital Twin
	 * @param models Lista de modelos JSON con las agrupaciones de metadatos a eliminar (tipo List<GroupFieldModel>)
	 * @param code Código del Digital Twin al que se desean eliminar los metadatos
	 * @return respuesta HTTP con el modelo JSON del Digital Twin (tipo TwinModel)
	 * y un código de respuesta 200 (OK)
	 */
	@RequestMapping(value = "/deleteMetadata/{code}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TwinModel> deleteMetadataToDigitalTwin(@RequestBody List<GroupFieldModel> models, 
			@PathVariable String code) {
		twinService.deleteMetadata(models, code);
		TwinModel model = twinService.findByCodigo(code);
		return new ResponseEntity<TwinModel>(model, HttpStatus.OK);
	}

}
