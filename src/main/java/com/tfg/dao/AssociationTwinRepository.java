package com.tfg.dao;

import org.springframework.data.repository.CrudRepository;

import com.tfg.model.AssociationTwin;
import com.tfg.model.id.AssociationTwinId;

/**
 * 
 * @author gcollada
 *
 */
public interface AssociationTwinRepository extends CrudRepository<AssociationTwin, AssociationTwinId>{

}
