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
	
	void addListAssociationsAsset(List<AssociationAsset> asociaciones);
	
	void deleteListAssociationsAsset(List<AssociationAsset> asociaciones);

}
