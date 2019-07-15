package com.tfg.controller.azure;
import javax.servlet.http.HttpSession;

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

import com.tfg.services.azure.DataFactoryService;
import com.tfg.services.model.DatasetModel;
import com.tfg.services.model.PipelineModel;

/**
 * Clase encargada de procesar las peticiones relacionadas con el servicio de Azure Data Factory
 * @author gcollada
 *
 */
@RestController
@RequestMapping(value = "/azure")
public class DataFactoryController {

	/**
	 * Servicio para realizar las operaciones sobre el Data Factory
	 */
	@Autowired
	DataFactoryService factoryService;

	/**
	 * Método que procesa la petición para autenticarse en Microsoft Active Directory para poder utilizar
	 * los distintos servicios que ofrece Azure
	 * @param session
	 * @return respuesta HTTP en formato JSON que incluye el token de autorización y un código 200 (OK)
	 */
	@RequestMapping(value = "/getBearerToken", method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getBearerToken(HttpSession session){
		JSONObject response = factoryService.getBearerToken(session);
		return new ResponseEntity<String>( "{\"response\":"+response.toString()+"}",
				HttpStatus.OK);
	}

	/**
	 * Método encargado de procesar la petición para crear/editar una canalización de datos
	 * @param session
	 * @param pipelineModel Modelo JSON con la información del Pipeline a crear/editar (tipo PipelineModel)
	 * @return respuesta HTTP en formato JSON que incluye la información del Pipleine
	 * y un código 201 (CREATED)
	 */
	@RequestMapping(value = "/setPipeline", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createPipeline(HttpSession session, @Validated @RequestBody PipelineModel pipelineModel){
		JSONObject response = factoryService.createPipeline(session, pipelineModel);
		return new ResponseEntity<String>("{\"response\":"+response.toString()+"}",
				HttpStatus.CREATED);
	}

	/**
	 * Método encargado de procesar la petición para obtener información sobre una canalización
	 * @param session
	 * @param PipelineName Nombre de la canalización de la que se desea obtener info
	 * @return respuesta HTTP en formato JSON que incluye la información del Pipleine
	 * y un código 200 (OK)
	 */
	@RequestMapping(value = "/getPipeline", method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getPipeline(HttpSession session, @RequestParam String PipelineName){
		JSONObject response = factoryService.getPipeline(session, PipelineName);
		return new ResponseEntity<String>("{\"response\":"+response.toString()+"}",
				HttpStatus.OK);
	}

	/**
	 * Método encargado de procesar la petición para ejecutar una canalización
	 * @param session
	 * @param PipelineName Nombre del Pipeline que se desea ejecutar
	 * @return Código HTTP de respuesta 200 (OK)
	 */
	@RequestMapping(value = "/runTrigger", method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> runTrigger(HttpSession session, @RequestParam String PipelineName){
		factoryService.runTrigger(session, PipelineName);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/**
	 * Método encargado de procesar la petición para crear/editar un conjunto de datos
	 * @param session
	 * @param datasetModel Modelo JSON con la información del Dataset a crear/editar (tipo DatasetModel)
	 * @return respuesta HTTP en formato JSON que incluye la información del Dataset
	 * y un código 201 (CREATED)
	 */
	@RequestMapping(value = "/setDataset", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> setDataset(HttpSession session, @Validated @RequestBody DatasetModel datasetModel){
		JSONObject response = factoryService.setDataset(session, datasetModel);
		return new ResponseEntity<String>("{\"response\":"+response.toString()+"}",
				HttpStatus.CREATED);
	}

	/**
	 * Método encargado de procesar la petición para obtener información sobre una conjunto de datos
	 * @param session
	 * @param DatasetName Nombre del conjunto del que se desea obtener información
	 * @return respuesta HTTP en formato JSON que incluye la información del Dataset
	 * y un código 200 (OK)
	 */
	@RequestMapping(value = "/getDataset", method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getDataset(HttpSession session, @RequestParam String DatasetName){
		JSONObject response = factoryService.getDataset(session, DatasetName);
		return new ResponseEntity<String>( "{\"response\":"+response.toString()+"}",
				HttpStatus.OK);
	}

}
