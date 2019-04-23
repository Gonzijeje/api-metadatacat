package com.tfg.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.tfg.model.DigitalTwin;

/**
 * 
 * @author gcollada
 *
 */
public interface DigitalTwinRepository extends CrudRepository<DigitalTwin, Long>{

	DigitalTwin findByCodigo(String codigo);
	
	@Transactional
	void deleteByCodigo(String codigo);
}
