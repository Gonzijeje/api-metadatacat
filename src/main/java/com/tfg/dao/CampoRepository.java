package com.tfg.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.tfg.model.Field;

/**
 * 
 * @author gcollada
 *
 */
public interface CampoRepository extends CrudRepository<Field, Long>{
	
	Field findByCodigo(String nombre);
	
	@Transactional
	void deleteByCodigo(String codigo);

}
