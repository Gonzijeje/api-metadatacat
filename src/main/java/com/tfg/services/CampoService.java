package com.tfg.services;

import java.util.List;

import com.tfg.model.Campo;

/**
 * 
 * @author gcollada
 *
 */
public interface CampoService {
	
	void add(Campo campo);
	
	Campo getMetadatoByCodigo(String nombre);
	
	List<Campo> getMetadatos();
	
	

}
