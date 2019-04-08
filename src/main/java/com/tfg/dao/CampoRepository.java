package com.tfg.dao;

import org.springframework.data.repository.CrudRepository;

import com.tfg.model.Campo;

/**
 * 
 * @author gcollada
 * @param <MetadatoRepository>
 *
 */
public interface CampoRepository extends CrudRepository<Campo, Long>{
	
	Campo findByCodigo(String nombre);

}
