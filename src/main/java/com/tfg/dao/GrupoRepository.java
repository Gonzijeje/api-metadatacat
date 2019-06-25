package com.tfg.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.tfg.model.Group;

/**
 * 
 * @author gcollada
 *
 */
public interface GrupoRepository extends CrudRepository<Group, Long>{
	
	Group findByCodigo(String codigo);
	
	@Transactional
	void deleteByCodigo(String codigo);

}
