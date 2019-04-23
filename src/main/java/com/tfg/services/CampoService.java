package com.tfg.services;

import java.util.List;
import java.util.Map;

import com.tfg.model.Campo;

/**
 * 
 * @author gcollada
 *
 */
public interface CampoService {
	
	boolean add(Campo campo);
	
	Campo create(Map<String,Object> payload);
	
	boolean delete(String codigo);
	
	Campo getCampoByCodigo(String nombre);
	
	List<Campo> getCampos();
	
	void addListCampos(List<String> campos);
	
}
