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
	
	void add(DigitalAsset da);
	
	void update(String codigo);
	
	void delete(String codigo);
	
	DigitalAsset findByCodigo(String codigo);
	
	List<DigitalAsset> getDigitalAssets();
	
	List<DigitalAsset> getDigitalAssetsByFilters(Map<String,Object> filters);
	

}
