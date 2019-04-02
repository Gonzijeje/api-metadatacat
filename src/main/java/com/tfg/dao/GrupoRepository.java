package com.tfg.dao;

import org.springframework.data.repository.CrudRepository;

import com.tfg.model.Grupo;

/**
 * 
 * @author gcollada
 *
 */
public interface GrupoRepository extends CrudRepository<Grupo, Integer>{
	
	Grupo findByCodigo(String codigo);

}
