package com.tfg.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.tfg.model.Campo;

/**
 * 
 * @author gcollada
 *
 */
public interface CampoRepository extends CrudRepository<Campo, Long>{
	
	Campo findByCodigo(String nombre);
	
	@Transactional
	void deleteByCodigo(String codigo);

}
