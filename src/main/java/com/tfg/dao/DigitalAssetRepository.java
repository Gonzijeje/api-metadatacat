package com.tfg.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.tfg.model.DigitalAsset;

/**
 * Repositorio para la entidad DigitalAsset. Gestiona una serie de operaciones CRUD sobre DigitalAssets.
 * @author gcollada
 *
 */
public interface DigitalAssetRepository extends JpaRepository<DigitalAsset, Long>{

	/**
	 * Busca un Digital Asset en la base de datos por su código
	 * @param codigo Código del DigitalAsset a buscar
	 * @return La entidad DigitalAsset encontrada
	 */
	DigitalAsset findByCodigo(String codigo);
	
	/**
	 * Elimina un DigitalAsset por su código
	 * @param codigo Código del DigitalAsset a eliminar
	 */
	@Transactional
	void deleteByCodigo(String codigo);
	

}
