package com.tfg.services;

import java.util.List;
import java.util.Map;

import com.tfg.model.DigitalTwin;

/**
 * 
 * @author gcollada
 *
 */
public interface DigitalTwinService{
	
	void add(DigitalTwin da);
	
	void update(String codigo);
	
	void delete(String codigo);
	
	DigitalTwin findByCodigo(String codigo);
	
	List<DigitalTwin> getDigitalTwins();
	
	List<DigitalTwin> getDigitalTwinsByFilters(Map<String,Object> filters);
	
}
