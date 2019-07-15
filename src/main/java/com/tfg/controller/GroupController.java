package com.tfg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tfg.model.Group;
import com.tfg.services.GroupService;
import com.tfg.services.adapters.GroupAdapter;
import com.tfg.services.model.GroupModel;
import com.tfg.services.model.NewGroup;

/**
 * Clase encargada de procesar las peticiones relacionadas con el recurso de Groups (grupos).
 * @author gcollada
 *
 */
@RestController
@RequestMapping(value = "/groups")
public class GroupController {

	/**
	 * Servicio para realizar operaciones lógicas sobre Groups
	 */
	@Autowired
	GroupService groupService;

	/**
	 * Método que procesa la petición para registrar un nuevo Group en el sistema
	 * @param newGroup Modelo JSON del nuevo Group a registrar (tipo NewGroup)
	 * @return respuesta HTTP con el modelo JSON ya creado del Group (tipo GroupModel)
	 * y un código de respuesta 201 (CREATED)
	 */
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GroupModel> registerGroup(@Validated @RequestBody NewGroup newGroup ) {
		Group grupo = groupService.create(newGroup);
		GroupModel model = groupService.add(grupo);
		return new ResponseEntity<GroupModel>(model, HttpStatus.CREATED);						
	}
	
	/**
	 * Método que procesa la petición para modificar un Group del sistema
	 * @param code Código del Group que se desea modificar
	 * @param newGroup Modelo JSON con la información que tendrá el Group modificado
	 * @return respuesta HTTP con el modelo JSON modificado del Group (tipo GroupModel)
	 * y un código de respuesta 200 (OK)
	 */
	@RequestMapping(value = "/{code}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GroupModel> updateGroup(@PathVariable String code, @Validated @RequestBody NewGroup newGroup ) {
		Group group = groupService.create(newGroup);
		GroupModel model = groupService.update(code, group);
		return new ResponseEntity<GroupModel>(model, HttpStatus.OK);
	}
	
	/**
	 * Método que procesa la petición para recuperar un Group por su código.
	 * @param code Código del Group que se desea recuperar
	 * @return respuesta HTTP con el modelo JSON del Group (tipo GroupModel)
	 * y un código de respuesta 200 (OK)
	 */
	@RequestMapping(value = "/{code}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GroupModel> getGroupByCode(@PathVariable String code) {
		GroupModel model = GroupAdapter.getGroupModel(groupService.getGroupByCode(code));
		return new ResponseEntity<GroupModel>(model, HttpStatus.OK);	
	}
	
	/**
	 * Método que procesa la petición para listar todos los Groups del sistema
	 * @return respuesta HTTP con una lista de los modelos JSON de los Group recuperados (tipo List<GroupModel>)
	 * y un código de respuesta 200 (OK)
	 */
	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<GroupModel>> listGroups(){
		List<GroupModel> listModels = groupService.getGroups();
		return new ResponseEntity<List<GroupModel>>(listModels, HttpStatus.OK);
	}

	/**
	 * Método que procesa la petición para borrar un Group
	 * @param code Código del Group que se desea borrar
	 * @return respuesta HTTP con un código 204 (NO CONTENT)
	 */
	@RequestMapping(value = "/{code}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GroupModel> deleteGroup(@PathVariable String code){
		groupService.delete(code);
		return new ResponseEntity<GroupModel>(HttpStatus.NO_CONTENT);
	}
}