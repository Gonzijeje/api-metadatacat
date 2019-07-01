package com.tfg.services;

import java.util.List;

import com.tfg.model.AssociationTwin;

/**
 * 
 * @author gcollada
 *
 */
public interface AssociationTwinService {
	
	void add(AssociationTwin ac);
	
	void addListAc_Twin(List<AssociationTwin> asociaciones);
	
	void deleteListAc_Twin(List<AssociationTwin> asociaciones);

}
