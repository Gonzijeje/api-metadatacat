package com.tfg.services;

import java.util.List;

import com.tfg.model.GroupField;
import com.tfg.pojos.FieldValueModel;

/**
 * 
 * @author gcollada
 *
 */
public interface GroupFieldService {
	
	void add(GroupField gc);
	
	void addListGrupo_Campo(List<GroupField> grcampos);
	
	List<FieldValueModel> getFieldsAndValuesByGroup(String groupCode, String assetCode);

}
