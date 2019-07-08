package com.tfg.dao;

import org.springframework.data.repository.CrudRepository;

import com.tfg.model.GroupField;
import com.tfg.model.id.GroupFieldId;

/**
 * 
 * @author gcollada
 *
 */
public interface GroupFieldRepository extends CrudRepository<GroupField, GroupFieldId>{
	
}
