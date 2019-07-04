package com.tfg.services;

import java.util.List;

import com.tfg.model.Group;
import com.tfg.services.model.GroupModel;
import com.tfg.services.model.NewGroup;

/**
 * 
 * @author gcollada
 *
 */
public interface GroupService {
	
	GroupModel add(Group group);
	
	GroupModel update(String code, Group group);
	
	Group create(NewGroup newGroup);
	
	void delete(String code);
	
	Group getGroupByCode(String code);
	
	List<GroupModel> getGroups();
	
	void addListGroups(List<Group> groups);
	
	boolean checkListGroups(List<String> groups);

}
