package com.tfg.services;

import java.util.List;

import com.tfg.model.AssociationTwin;

/**
 * Interfaz de operaciones definidas relacionadas con los AssociationTwin
 * @author gcollada
 *
 */
public interface AssociationTwinService {
	
	/**
	 * Método para añadir un AssociationTwin al sistema.
	 * @param ac Objeto AssociationTwin a insertar
	 */
	void add(AssociationTwin ac);
	
	/**
	 * Método para añadir una lista de AssociationTwin al sistema
	 * @param asociaciones Lista de objetos AssociationTwin a insertar
	 */
	void addListAssociationsTwin(List<AssociationTwin> asociaciones);
	
	/**
	 * Método para eliminar una lista de AssociationTwin al sistema
	 * @param asociaciones Lista de objetos AssociationTwin a eliminar
	 */
	void deleteListAssociationsTwin(List<AssociationTwin> asociaciones);

}
