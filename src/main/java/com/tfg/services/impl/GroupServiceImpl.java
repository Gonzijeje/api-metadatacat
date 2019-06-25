package com.tfg.services.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.adapters.GroupAdapter;
import com.tfg.dao.GrupoRepository;
import com.tfg.model.Group;
import com.tfg.pojos.GroupModel;
import com.tfg.pojos.NewGroup;
import com.tfg.services.GroupService;

/**
 * 
 * @author gcollada
 *
 */
@Service
public class GroupServiceImpl implements GroupService{
	
	@Autowired
	GrupoRepository repository;

	@Override
	public GroupModel add(Group group) {
		if(repository.findByCodigo(group.getCodigo())==null) {
			repository.save(group);
			return GroupAdapter.getGroupModel(group);
		}
		return null;
	}
	
	@Override
	public boolean delete(String codigo) {
		if(repository.findByCodigo(codigo)!=null) {
			repository.deleteByCodigo(codigo);
			return true;
		}
		return false;
	}

	@Override
	public GroupModel getGrupoByCodigo(String nombre) {
		Group group = repository.findByCodigo(nombre);
		return GroupAdapter.getGroupModel(group);
	}

	@Override
	public List<GroupModel> getGrupos() {
		List<GroupModel> models = new ArrayList<GroupModel>(
				GroupAdapter.getGroupModel(repository.findAll()));
		return models;
	}
	
	@Override
	public void addListGrupos(List<String> grupos) {
		List<Group> lista = new ArrayList<Group>();
		grupos.forEach((grupo)-> {
			if(repository.findByCodigo(grupo)==null) {
				lista.add(new Group(grupo,""));
			}
		});
		repository.saveAll(lista);
	}

	@Override
	public Group create(NewGroup newGroup) {
		return GroupAdapter.getGroupEntity(newGroup);
	}

	@Override
	public boolean checkListGrupos(List<String> grupos) {
		return (grupos.stream().noneMatch((grupo)-> repository.findByCodigo(grupo) == null));	
	}
}
