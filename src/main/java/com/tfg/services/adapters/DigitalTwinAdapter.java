package com.tfg.adapters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tfg.model.Ac_Twin;
import com.tfg.model.DigitalTwin;
import com.tfg.model.Field;
import com.tfg.model.Group;
import com.tfg.model.GroupField;
import com.tfg.model.Value;
import com.tfg.pojos.TwinModel;
import com.tfg.pojos.GroupFieldModel;
import com.tfg.pojos.NewTwin;
import com.tfg.services.GroupFieldService;
import com.tfg.services.impl.GroupFieldServiceImpl;

public class DigitalTwinAdapter {
	
	@Autowired
	static GroupFieldService groupFieldService = new GroupFieldServiceImpl();
	
	public static DigitalTwin getTwinEntity(NewTwin newTwin) {
		DigitalTwin twin = new DigitalTwin();
		twin.setCodigo(newTwin.getCode());
		twin.setDescripcion(newTwin.getDescription());
		return twin;
	}
	
	public static DigitalTwin getTwinEntity(TwinModel twinModel) {
		DigitalTwin twin = new DigitalTwin();
		twin.setId(twinModel.getId());
		twin.setCodigo(twinModel.getCode());
		twin.setDescripcion(twinModel.getDescription());
		return twin;
	}
	
	public static TwinModel getDigitalTwinModel(DigitalTwin twin) {
		TwinModel model = new TwinModel();
		model.setId(twin.getId());
		model.setCode(twin.getCodigo());
		model.setDescription(twin.getDescripcion());
		model.setGrupos(getCaca(twin));
		model.setAssetsIn(DigitalAssetAdapter.getSimpleAssetsFromEntities(twin.getAsociaciones_assets_in()));
		model.setAssetsOut(DigitalAssetAdapter.getSimpleAssetsFromEntities(twin.getAsociaciones_assets_out()));
		return model;
	}
	
	public static List<TwinModel> getDigitalTwinModel(Iterable<DigitalTwin> fields) {
		List<TwinModel> models = new ArrayList<TwinModel>();
		fields.forEach((field)->{
			models.add(getDigitalTwinModel(field));
		});
		return models;
	}
	
	public static List<Field> getFieldsAssociatedToTwin(NewTwin newTwin){
		List<Field> listFields = new ArrayList<Field>();
		for(String field : newTwin.getAttributes()) {
			listFields.add(new Field(field,"Metadato "+field+" básico"));
		}
		return listFields;
	}

	public static List<GroupField> getGroupFieldsAssociatedToTwin(Group group,
			List<Field> listFields, List<Value> listValues){
		List<GroupField> listGroupFields = new ArrayList<GroupField>();
		for(int i=0; i < listFields.size(); i++) {
			listGroupFields.add(new GroupField(group,listFields.get(i),
					listValues.get(i)));
		}
		return listGroupFields;
	}
	
	//Esto va a tener que ir para otra clase
	public static List<GroupFieldModel> getCaca(DigitalTwin twin){
		List<GroupFieldModel> list = new ArrayList<GroupFieldModel>();
		for(Ac_Twin ac : twin.getAsociaciones_twin()) {
			if(list.isEmpty() || !list.stream().anyMatch(grupo -> grupo.getGroupCode().equals(ac.getGrupo().getCodigo()))) {
				GroupFieldModel model = new GroupFieldModel();
				model.setGroupCode(ac.getGrupo().getCodigo());
				list.add(model);
			}		
		}
		list.forEach((grupo)->{
			grupo.setFields(groupFieldService.getFieldsAndValuesByGroup(grupo.getGroupCode(),twin.getCodigo()));
		});
		return list;
	}

}
