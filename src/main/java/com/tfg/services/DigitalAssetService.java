package com.tfg.services;

import java.util.List;
import java.util.Map;

import com.tfg.model.DigitalAsset;
import com.tfg.pojos.AssetModel;
import com.tfg.pojos.GroupFieldModel;
import com.tfg.pojos.NewAsset;

/**
 * 
 * @author gcollada
 *
 */
public interface DigitalAssetService{
	
	void add(NewAsset newAsset, DigitalAsset asset);
	
	void addRealAsset(String codigo, NewAsset newAsset);
	
	DigitalAsset create(NewAsset newAsset);
	
	void delete(String codigo);
	
	AssetModel findByCodigo(String codigo);
	
	List<AssetModel> getDigitalAssets();
	
	List<AssetModel> getDigitalAssetsByFilters(Map<String,Object> filters);
	
	void addMetadata(List<GroupFieldModel> models, String code);
	
	void deleteMetadata(List<GroupFieldModel> models, String code);
	
	boolean checkListAssets(List<String> codes);

}
