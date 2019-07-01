package com.tfg.services;

import java.util.List;

import com.tfg.model.AssociationAsset;

/**
 * 
 * @author gcollada
 *
 */
public interface AssociationAssetService {
	
	void add(AssociationAsset ac);
	
	void addListAc_Asset(List<AssociationAsset> asociaciones);
	
	void deleteListAc_Asset(List<AssociationAsset> asociaciones);

}
