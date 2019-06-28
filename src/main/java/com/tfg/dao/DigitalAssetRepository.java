package com.tfg.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.tfg.model.DigitalAsset;

/**
 * 
 * @author gcollada
 *
 */
public interface DigitalAssetRepository extends JpaRepository<DigitalAsset, Long>{

	DigitalAsset findByCodigo(String codigo);
	
	@Transactional
	void deleteByCodigo(String codigo);
	

}
