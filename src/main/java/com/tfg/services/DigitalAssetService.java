package com.tfg.services;

import java.util.List;
import java.util.Map;

import com.tfg.model.DigitalAsset;

/**
 * 
 * @author gcollada
 *
 */
public interface DigitalAssetService{
	
	int add(DigitalAsset da);
	
	DigitalAsset create(Map<String,Object> payload);
	
	void update(String codigo);
	
	boolean delete(String codigo);
	
	DigitalAsset findByCodigo(String codigo);
	
	List<DigitalAsset> getDigitalAssets();
	
	List<DigitalAsset> getDigitalAssetsByFilters(Map<String,Object> filters);
	

}
