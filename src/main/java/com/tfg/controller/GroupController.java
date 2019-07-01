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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tfg.model.Group;
import com.tfg.services.GroupService;
import com.tfg.services.adapters.GroupAdapter;
import com.tfg.services.model.GroupModel;
import com.tfg.services.model.NewGroup;

/**
 * 
 * @author gcollada
 *
 */
@RestController
@RequestMapping(value = "/groups")
public class GroupController {

	@Autowired
	GroupService groupService;

	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GroupModel> registerGroup(@Validated @RequestBody NewGroup newGroup ) {
		Group grupo = groupService.create(newGroup);
		GroupModel model = groupService.add(grupo);
		return new ResponseEntity<GroupModel>(model, HttpStatus.CREATED);						
	}
	
	@RequestMapping(value = "/{code}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GroupModel> updateGroup(@PathVariable String code, @Validated @RequestBody NewGroup newGroup ) {
		Group group = groupService.create(newGroup);
		GroupModel model = groupService.update(code, group);
		return new ResponseEntity<GroupModel>(model, HttpStatus.ACCEPTED);
	}
	
	@RequestMapping(value = "/{code}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GroupModel> getGroupByCode(@PathVariable String code) {
		GroupModel model = GroupAdapter.getGroupModel(groupService.getGrupoByCodigo(code));
		return new ResponseEntity<GroupModel>(model, HttpStatus.OK);	
	}
	
	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<GroupModel>> listGroups(){
		List<GroupModel> listModels = groupService.getGrupos();
		return new ResponseEntity<List<GroupModel>>(listModels, HttpStatus.OK);
	}

	@RequestMapping(value = "/{code}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GroupModel> deleteGroup(@RequestParam String codigo){
		groupService.delete(codigo);
		return new ResponseEntity<GroupModel>(HttpStatus.ACCEPTED);
	}
}