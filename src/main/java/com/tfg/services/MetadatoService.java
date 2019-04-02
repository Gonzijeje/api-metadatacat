package com.tfg.services;

import java.util.List;

import com.tfg.model.Metadato;

/**
 * 
 * @author gcollada
 *
 */
public interface MetadatoService {
	
	void add(Metadato metadato);
	
	Metadato getMetadatoByCodigo(String nombre);
	
	List<Metadato> getMetadatos();
	
	

}
