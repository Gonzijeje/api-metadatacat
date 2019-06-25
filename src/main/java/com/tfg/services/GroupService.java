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
	
	Group create(NewGroup payload);
	
	boolean delete(String codigo);
	
	GroupModel getGrupoByCodigo(String nombre);
	
	List<GroupModel> getGrupos();
	
	void addListGrupos(List<String> grupos);
	
	boolean checkListGrupos(List<String> grupos);

}
