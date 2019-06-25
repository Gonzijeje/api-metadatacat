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

import com.tfg.model.Group;
import com.tfg.pojos.GroupModel;
import com.tfg.pojos.NewGroup;
import com.tfg.services.GroupService;

/**
 * 
 * @author gcollada
 *
 */
@RestController
@RequestMapping(value = "/groups")
public class GrupoController {

	@Autowired
	GroupService groupService;

	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GroupModel> registerGroup(@Validated @RequestBody NewGroup newGroup ) {
		try {
			Group grupo = groupService.create(newGroup);
			GroupModel model = groupService.add(grupo);
			return new ResponseEntity<GroupModel>(model, HttpStatus.CREATED);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null; //
						
	}
	
	@RequestMapping(value = "/{code}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GroupModel> getGroupByCode(@PathVariable String code) {
		try {
			GroupModel model = groupService.getGrupoByCodigo(code);
			return new ResponseEntity<GroupModel>(model, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;	
	}
	
	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GroupModel>> listGroups(){
		List<GroupModel> listModels = groupService.getGrupos();
		return new ResponseEntity<List<GroupModel>>(listModels, HttpStatus.OK); //
    }

	@RequestMapping(value = "/{code}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GroupModel> deleteGroup(@RequestParam String codigo){
		if(groupService.delete(codigo)) {
			return new ResponseEntity<GroupModel>(HttpStatus.ACCEPTED);
		}else {
			////////////////////
		}
		return null;
	}
}