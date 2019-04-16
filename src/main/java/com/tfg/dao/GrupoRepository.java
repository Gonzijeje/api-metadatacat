package com.tfg.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.tfg.model.Grupo;

/**
 * 
 * @author gcollada
 *
 */
public interface GrupoRepository extends CrudRepository<Grupo, Long>{
	
	Grupo findByCodigo(String codigo);
	
	@Transactional
	void deleteByCodigo(String codigo);

}
