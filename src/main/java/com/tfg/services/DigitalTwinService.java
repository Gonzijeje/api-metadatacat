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
	
	int add(DigitalTwin da);
	
	DigitalTwin create(Map<String,Object> payload);
	
	void update(String codigo);
	
	boolean delete(String codigo);
	
	DigitalTwin findByCodigo(String codigo);
	
	List<DigitalTwin> getDigitalTwins();
	
	List<DigitalTwin> getDigitalTwinsByFilters(Map<String,Object> filters);
	
}
