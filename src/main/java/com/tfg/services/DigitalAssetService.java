package com.tfg.services;

import java.util.List;
import java.util.Map;

import com.tfg.model.DigitalAsset;
import com.tfg.pojos.AssetModel;
import com.tfg.pojos.NewAsset;

/**
 * 
 * @author gcollada
 *
 */
public interface DigitalAssetService{
	
	AssetModel add(NewAsset newAsset, DigitalAsset asset);
	
	AssetModel update(String codigo, DigitalAsset asset);
	
	DigitalAsset create(NewAsset newAsset);
	
	void delete(String codigo);
	
	AssetModel findByCodigo(String codigo);
	
	List<AssetModel> getDigitalAssets();
	
	List<AssetModel> getDigitalAssetsByFilters(Map<String,Object> filters);
	

}
