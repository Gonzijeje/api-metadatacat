package com.tfg.services;

import java.util.List;

import com.tfg.model.GroupField;
import com.tfg.services.model.FieldValueModel;

/**
 * 
 * @author gcollada
 *
 */
public interface GroupFieldService {
	
	void add(GroupField gc);
	
	void addListGroupFields(List<GroupField> groupFields);
	
	void deleteListGroupFields(List<GroupField> groupFields);
	
	List<FieldValueModel> getFieldsAndValuesByGroup(String groupCode, String assetCode);

}
