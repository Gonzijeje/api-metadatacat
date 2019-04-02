package com.tfg.services;

import java.util.List;
import java.util.Map;

import com.tfg.model.DigitalAsset;

public interface DigitalAssetService{
	
	void add(DigitalAsset da);
	
	void update(int id);
	
	void delete(int id);
	
	List<DigitalAsset> getDigitalAssets();
	
	List<DigitalAsset> getDigitalAssetsByFilter(String criterio, Object valor);
	
	List<DigitalAsset> getDigitalAssetsByFilters(Map<String,Object> filters);
	

}
