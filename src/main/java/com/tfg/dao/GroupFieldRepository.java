package com.tfg.dao;

import org.springframework.data.repository.CrudRepository;

import com.tfg.model.GroupField;
import com.tfg.model.id.GroupFieldId;

/**
 * Repositorio para la entidad GroupField. Gestiona una serie de operaciones CRUD sobre GroupFields.
 * @author gcollada
 *
 */
public interface GroupFieldRepository extends CrudRepository<GroupField, GroupFieldId>{
	
}
