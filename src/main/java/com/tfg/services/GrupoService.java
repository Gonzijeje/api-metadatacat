package com.tfg.services;

import java.util.List;

import com.tfg.model.Grupo;

/**
 * 
 * @author gcollada
 *
 */
public interface GrupoService {
	
	void add(Grupo grupo);
	
	void delete(String codigo);
	
	Grupo getGrupoByCodigo(String nombre);
	
	List<Grupo> getGrupos();

}
