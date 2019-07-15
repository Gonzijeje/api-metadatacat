package com.tfg.services.adapters;

import java.util.ArrayList;
import java.util.List;

import com.tfg.model.Group;
import com.tfg.services.model.GroupModel;
import com.tfg.services.model.NewGroup;

/**
 * Clase encargada de adaptar y realizar las transformaciones entre los
 * distintos modelos de un Group y su entidad
 * @author gcollada
 *
 */
public class GroupAdapter {
	
	/**
	 * Método encargado de transformar un modelo de nuevo Group a una entidad Group
	 * @param newGroup El módelo JSON de tipo NewGroup
	 * @return un objeto Group entidad
	 */
	public static Group getGroupEntity(NewGroup newGroup) {
		Group group = new Group();
		group.setCodigo(newGroup.getCode());
		group.setDescripcion(newGroup.getDescription());
		return group;
	}
	
	/**
	 * Método encargado de transformar una entidad Group a un modelo GroupModel
	 * @param group El objeto Group entidad
	 * @return un objeto GroupModel
	 */
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
