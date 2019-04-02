package com.tfg.services;

import com.tfg.model.Grupo;

/**
 * 
 * @author gcollada
 *
 */
public interface GrupoService {
	
	void add(Grupo grupo);
	
	void delete(int id);
	
	Grupo getGrupoByCodigo(String nombre);

}
