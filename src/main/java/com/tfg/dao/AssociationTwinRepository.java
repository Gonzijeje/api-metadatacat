package com.tfg.dao;

import org.springframework.data.repository.CrudRepository;

import com.tfg.model.AssociationTwin;
import com.tfg.model.id.AssociationTwinId;

/**
 * Repositorio para la entidad AssociationTwin. Gestiona una serie de operaciones CRUD sobre AssociationTwins
 * @author gcollada
 *
 */
public interface AssociationTwinRepository extends CrudRepository<AssociationTwin, AssociationTwinId>{

}
