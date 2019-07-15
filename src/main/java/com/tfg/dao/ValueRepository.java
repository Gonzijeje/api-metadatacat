package com.tfg.dao;

import org.springframework.data.repository.CrudRepository;

import com.tfg.model.Value;

/**
 * Repositorio para la entidad Value. Gestiona una serie de operaciones CRUD sobre Values.
 * @author gcollada
 *
 */
public interface ValueRepository extends CrudRepository<Value, Long>{
	
	/**
	 * Busca un objeto Value en la base de datos por su valor asociado
	 * @param valor Valor de la entidad Value a buscar
	 * @return el objeto Value encontrado
	 */
	Value findByValor(Object valor);

}
