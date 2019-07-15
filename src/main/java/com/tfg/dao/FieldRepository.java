package com.tfg.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.tfg.model.Field;

/**
 * Repositorio para la entidad Field. Gestiona una serie de operaciones CRUD sobre Fields.
 * @author gcollada
 *
 */
public interface FieldRepository extends CrudRepository<Field, Long>{
	
	Field findByCodigo(String nombre);
	
	@Transactional
	void deleteByCodigo(String codigo);

}
