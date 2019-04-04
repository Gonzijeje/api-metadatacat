package com.tfg.dao;

import org.springframework.data.repository.CrudRepository;

import com.tfg.model.DigitalAsset;

/**
 * 
 * @author gcollada
 *
 */
public interface DigitalAssetRepository extends CrudRepository<DigitalAsset, Integer>{

	DigitalAsset findByCodigo(String codigo);
	
	void deleteByCodigo(String codigo);

}
