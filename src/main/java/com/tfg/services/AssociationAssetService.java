package com.tfg.services;

import java.util.List;

import com.tfg.model.AssociationAsset;

/**
 * Interfaz de operaciones definidas relacionadas con los AssociationAsset
 * @author gcollada
 *
 */
public interface AssociationAssetService {
	
	/**
	 * Método para añadir un AssociationAsset al sistema.
	 * @param ac Objeto AssociationAsset a insertar
	 */
	void add(AssociationAsset ac);
	
	/**
	 * Método para añadir una lista de AssociationAsset al sistema
	 * @param asociaciones Lista de objetos AssociationAsset a insertar
	 */
	void addListAssociationsAsset(List<AssociationAsset> asociaciones);
	
	/**
	 * Método para eliminar una lista de AssociationAsset al sistema
	 * @param asociaciones Lista de objetos AssociationAsset a eliminar
	 */
	void deleteListAssociationsAsset(List<AssociationAsset> asociaciones);

}
