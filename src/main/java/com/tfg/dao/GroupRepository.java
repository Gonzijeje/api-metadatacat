package com.tfg.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.tfg.model.Group;

/**
 * Repositorio para la entidad Group. Gestiona una serie de operaciones CRUD sobre Groups.
 * @author gcollada
 *
 */
public interface GroupRepository extends CrudRepository<Group, Long>{
	
	Group findByCodigo(String codigo);
	
	@Transactional
	void deleteByCodigo(String codigo);

}
