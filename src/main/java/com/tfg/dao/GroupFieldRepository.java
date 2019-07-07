package com.tfg.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tfg.model.GroupField;
import com.tfg.model.id.GroupFieldId;

/**
 * 
 * @author gcollada
 *
 */
public interface GroupFieldRepository extends CrudRepository<GroupField, GroupFieldId>{
	
	@Query(value = "SELECT c.code, v.value FROM Field c, Value v, GroupField gc, Group g, DigitalAsset da, Ac_Asset ac "
			+ "where da.id=ac.asset_id and ac.ac_group_id=gc.group_id and ac.ac_field_id=gc.field_id and ac.ac_value_id=gc.value_id "
			+ "and c.id=gc.field_id and v.id=gc.value_id and g.id=gc.group_id and g.code=?1 and da.code=?2 group by c.code,v.value",
			nativeQuery = true)
	List<Object[]> getFieldsAndValuesByGroup(String groupCode, String assetCode);

}
