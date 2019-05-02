package com.tfg.dao;

import org.springframework.data.repository.CrudRepository;

import com.tfg.model.Ac_Asset;
import com.tfg.model.id.Ac_Asset_Id;

/**
 * 
 * @author gcollada
 *
 */
public interface Ac_AssetRepository extends CrudRepository<Ac_Asset, Ac_Asset_Id>{

}
