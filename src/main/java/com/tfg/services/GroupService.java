package com.tfg.services;

import java.util.List;

import com.tfg.model.Group;
import com.tfg.pojos.GroupModel;
import com.tfg.pojos.NewGroup;

/**
 * 
 * @author gcollada
 *
 */
public interface GroupService {
	
	GroupModel add(Group group);
	
	GroupModel update(String code, Group group);
	
	Group create(NewGroup payload);
	
	void delete(String codigo);
	
	Group getGrupoByCodigo(String nombre);
	
	List<GroupModel> getGrupos();
	
	void addListGrupos(List<Group> grupos);
	
	boolean checkListGrupos(List<String> grupos);

}
