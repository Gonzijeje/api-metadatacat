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
	
	void addListAssociationsTwin(List<AssociationTwin> asociaciones);
	
	void deleteListAssociationsTwin(List<AssociationTwin> asociaciones);

}
