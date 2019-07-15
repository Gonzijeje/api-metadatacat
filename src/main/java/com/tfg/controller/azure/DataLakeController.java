package com.tfg.controller.azure;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tfg.services.azure.DataLakeService;

/**
 * Clase encargada de procesar las peticiones relacionadas con el servicio de Azure Data Lake
 * @author gcollada
 *
 */
@RestController
@RequestMapping(value = "/azure")
public class DataLakeController {
	
	/**
	 * Servicio para realizar operaciones sobre el Data Lake
	 */
	@Autowired
	DataLakeService dlService;
	
	/**
	 * Método que procesa la petición para descargar un archivo contenido en el Data Lake
	 * @param session 
	 * @param pathFile Ruta donde se encuentra el archivo en el Data Lake (tipo String)
	 * @return código de respuesta Http 200 (OK)
	 */
	@RequestMapping(value = "/download", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONObject> download(HttpSession session, @RequestParam String pathFile){
		dlService.download(session, pathFile);
		return new ResponseEntity<JSONObject>(HttpStatus.OK);
	}

}
