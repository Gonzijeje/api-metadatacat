package com.tfg.dao;

import org.springframework.data.repository.CrudRepository;

import com.tfg.model.AssociationAsset;
import com.tfg.model.id.AssociationAssetId;

/**
 * Repositorio para la entidad AssociationAsset. Gestiona una serie de operaciones CRUD sobre AssociationAssets
 * @author gcollada
 *
 */
public interface AssociationAssetRepository extends CrudRepository<AssociationAsset, AssociationAssetId>{

}
