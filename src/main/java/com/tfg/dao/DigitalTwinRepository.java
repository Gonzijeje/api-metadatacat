package com.tfg.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.tfg.model.DigitalTwin;

/**
 * Repositorio para la entidad DigitalTwin. Gestiona una serie de operaciones CRUD sobre DigitalTwins.
 * @author gcollada
 *
 */
public interface DigitalTwinRepository extends CrudRepository<DigitalTwin, Long>{

	/**
	 * Busca un Digital Twin en la base de datos por su código
	 * @param codigo Código del DigitalTwin a buscar
	 * @return La entidad DigitalTwin encontrada
	 */
	DigitalTwin findByCodigo(String codigo);
	
	/**
	 * Elimina un DigitalTwin por su código
	 * @param codigo Código del DigitalTwin a eliminar
	 */
	@Transactional
	void deleteByCodigo(String codigo);
}
