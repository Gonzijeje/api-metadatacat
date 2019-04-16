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
	
	void delete(String codigo);
	
	Campo getCampoByCodigo(String nombre);
	
	List<Campo> getCampos();
	
	void addListCampos(List<String> campos);
	
	

}
