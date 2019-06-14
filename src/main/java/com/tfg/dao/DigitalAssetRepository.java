package com.tfg.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.tfg.model.DigitalAsset;

/**
 * 
 * @author gcollada
 *
 */
public interface DigitalAssetRepository extends CrudRepository<DigitalAsset, Long>{

	DigitalAsset findByCodigo(String codigo);
	
	@Transactional
	void deleteByCodigo(String codigo);
	

}
