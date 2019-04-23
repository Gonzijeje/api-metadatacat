package com.tfg.services;

import java.util.List;
import java.util.Map;

import com.tfg.model.Grupo;

/**
 * 
 * @author gcollada
 *
 */
public interface GrupoService {
	
	boolean add(Grupo grupo);
	
	Grupo create(Map<String,Object> payload);
	
	boolean delete(String codigo);
	
	Grupo getGrupoByCodigo(String nombre);
	
	List<Grupo> getGrupos();

}
