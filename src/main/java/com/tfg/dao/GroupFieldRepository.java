package com.tfg.dao;

import org.springframework.data.repository.CrudRepository;

import com.tfg.model.GroupField;
import com.tfg.model.id.Grupo_Campo_Id;

/**
 * 
 * @author gcollada
 *
 */
public interface GroupFieldRepository extends CrudRepository<GroupField, Grupo_Campo_Id>{
	

}
