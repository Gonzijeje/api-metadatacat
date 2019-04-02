package com.tfg.dao;

import org.springframework.data.repository.CrudRepository;

import com.tfg.model.Metadato;

/**
 * 
 * @author gcollada
 * @param <MetadatoRepository>
 *
 */
public interface MetadatoRepository extends CrudRepository<Metadato, Integer>{
	
	Metadato findByCodigo(String nombre);

}
