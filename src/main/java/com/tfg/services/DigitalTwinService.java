package com.tfg.services;

import java.util.List;
import java.util.Map;

import com.tfg.model.DigitalTwin;
import com.tfg.pojos.GroupFieldModel;
import com.tfg.pojos.NewTwin;
import com.tfg.pojos.TwinModel;

/**
 * 
 * @author gcollada
 *
 */
public interface DigitalTwinService{
	
	void add(NewTwin newTwin, DigitalTwin dt);

	DigitalTwin create(NewTwin newTwin);

	void delete(String codigo);

	TwinModel findByCodigo(String codigo);

	List<TwinModel> getDigitalTwins();

	List<TwinModel> getDigitalTwinsByFilters(Map<String,Object> filters);

	void addMetadata(List<GroupFieldModel> models, String code);

	void deleteMetadata(List<GroupFieldModel> models, String code);
	
}
