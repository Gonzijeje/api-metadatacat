package com.tfg.adapters;

import java.util.ArrayList;
import java.util.List;

import com.tfg.model.Group;
import com.tfg.pojos.GroupModel;
import com.tfg.pojos.NewGroup;

public class GroupAdapter {
	
	public static Group getGroupEntity(NewGroup newGroup) {
		Group group = new Group();
		group.setCodigo(newGroup.getCode());
		group.setDescripcion(newGroup.getDescription());
		return group;
	}
	
	public static GroupModel getGroupModel(Group group) {
		GroupModel model = new GroupModel();
		model.setCode(group.getCodigo());
		model.setDescription(group.getDescripcion());
		return model;
	}
	
	public static List<GroupModel> getGroupModel(Iterable<Group> groups) {
		List<GroupModel> models = new ArrayList<GroupModel>();
		groups.forEach((group)->{
			models.add(getGroupModel(group));
		});
		return models;
	}

	public static Group getGroupEntity(GroupModel groupModel) {
		Group group = new Group();
		group.setCodigo(groupModel.getCode());
		group.setDescripcion(groupModel.getDescription());
		return group;
	}

}
