package com.tfg.services;

import java.util.List;

import com.tfg.model.Metadato;

/**
 * 
 * @author gcollada
 *
 */
public interface MetadatoService {
	
	Metadato getMetadatoByCodigo(String nombre);
	
	List<Metadato> getMetadatos();
	
	void add(Metadato metadato);

}
