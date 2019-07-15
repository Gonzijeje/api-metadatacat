package com.tfg.services.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.dao.GroupRepository;
import com.tfg.exceptions.ExceptionFactory;
import com.tfg.exceptions.ExceptionFactory.Errors;
import com.tfg.model.Group;
import com.tfg.services.GroupService;
import com.tfg.services.adapters.GroupAdapter;
import com.tfg.services.model.GroupModel;
import com.tfg.services.model.NewGroup;

/**
 * Implementación de la interfaz de operaciones para Groups
 * @author gcollada
 *
 */
@Service
public class GroupServiceImpl implements GroupService{
	
	@Autowired
	GroupRepository repository;

	@Override
	public GroupModel add(Group group) {
		if(repository.findByCodigo(group.getCodigo())==null) {
			repository.save(group);
			return GroupAdapter.getGroupModel(group);
		}else {
			throw ExceptionFactory.getError(Errors.UNIQUE_CODE);
		}
	}
	
	@Override
	public GroupModel update(String code, Group group) {
		Group oldGroup = repository.findByCodigo(code);
		if(oldGroup!=null) {
			group.setId(oldGroup.getId());
			String newCode = group.getCodigo();
			if(!newCode.equals(code) && repository.findByCodigo(newCode)!=null) {
				throw ExceptionFactory.getError(Errors.UNIQUE_CODE);
			}else {
				repository.save(group);
				return GroupAdapter.getGroupModel(group);
			}		
		}else {
			throw ExceptionFactory.getError(Errors.ENTITY_NOT_FOUND);
		}
	}
	
	@Override
	public void delete(String codigo) {
		if(repository.findByCodigo(codigo)!=null) {
			repository.deleteByCodigo(codigo);
		}else {
			throw ExceptionFactory.getError(Errors.ENTITY_NOT_FOUND);
		}
	}

	@Override
	public Group getGroupByCode(String nombre) {
		Group group = repository.findByCodigo(nombre);
		if(group!=null) {
			return group;
		}			
		else {
			throw ExceptionFactory.getError(Errors.ENTITY_NOT_FOUND);
		}
	}

	@Override
	public List<GroupModel> getGroups() {
		List<GroupModel> models = new ArrayList<GroupModel>(
				GroupAdapter.getGroupModel(repository.findAll()));
		return models;
	}
	
	@Override
	public void addListGroups(List<Group> grupos) {
		List<Group> lista = new ArrayList<Group>();
		grupos.forEach((grupo)-> {
			if(repository.findByCodigo(grupo.getCodigo()) == null) {
				lista.add(grupo);
			}
		});
		repository.saveAll(lista);
	}

	@Override
	public Group create(NewGroup newGroup) {
		return GroupAdapter.getGroupEntity(newGroup);
	}

	@Override
	public boolean checkListGroups(List<String> grupos) {
		return (grupos.stream().noneMatch((grupo)-> repository.findByCodigo(grupo) == null));	
	}

}
