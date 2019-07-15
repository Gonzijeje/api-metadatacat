package com.tfg.services.adapters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tfg.model.AssociationTwin;
import com.tfg.model.DigitalTwin;
import com.tfg.model.Field;
import com.tfg.model.Group;
import com.tfg.model.GroupField;
import com.tfg.model.Value;
import com.tfg.services.GroupFieldService;
import com.tfg.services.impl.GroupFieldServiceImpl;
import com.tfg.services.model.GroupFieldModel;
import com.tfg.services.model.NewTwin;
import com.tfg.services.model.TwinModel;

/**
 * Clase encargada de adaptar y realizar las transformaciones entre los
 * distintos modelos de un DigitalTwin y su entidad
 * @author gcollada
 *
 */
public class DigitalTwinAdapter {
	
	@Autowired
	static GroupFieldService groupFieldService = new GroupFieldServiceImpl();
	
	/**
	 * Método encargado de transformar un módelo de nuevo DigitalTwin a una entidad DigitalTwin
	 * @param newTwin El módelo JSON de tipo NewTwin
	 * @return un objeto DigitalTwin entidad
	 */
	public static DigitalTwin getTwinEntity(NewTwin newTwin) {
		DigitalTwin twin = new DigitalTwin();
		twin.setCodigo(newTwin.getCode());
		twin.setDescripcion(newTwin.getDescription());
		return twin;
	}
	
	/**
	 * Método encargado de transformar un módelo de DigitalTwin a una entidad DigitalTwin
	 * @param twinModel El módelo JSON de tipo TwinModel
	 * @return un objeto DigitalTwin entidad
	 */
	public static DigitalTwin getTwinEntity(TwinModel twinModel) {
		DigitalTwin twin = new DigitalTwin();
		twin.setId(twinModel.getId());
		twin.setCodigo(twinModel.getCode());
		twin.setDescripcion(twinModel.getDescription());
		return twin;
	}
	
	/**
	 * Método encargado de transformar una entidad DigitalTwin en un modelo DTO TwinModel con
	 * información de la propia entidad DigitalTwin y de las agrupaciones de metadatos que tiene asociadas
	 * @param twin La entidad DigitalTwin que transformar a modelo
	 * @return Un objeto TwinModel con todos los metadatos del gemelo digital
	 */
	public static TwinModel getDigitalTwinModel(DigitalTwin twin) {
		TwinModel model = new TwinModel();
		model.setId(twin.getId());
		model.setCode(twin.getCodigo());
		model.setDescription(twin.getDescripcion());
		model.setGrupos(getGroupFieldModels(twin));
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
	
	/**
	 * Método que se encarga de obtener las agrupaciones de metadatos de un DigitalTwin a partir
	 * de los grupos que tenga asociados en sus asociaciones AssociationTwin.
	 * @param twin el objeto DigitalTwin del que obtener las agrupaciones de metadatos
	 * @return La lista de agrupaciones de metadatos del DigitalTwin
	 */
	public static List<GroupFieldModel> getGroupFieldModels(DigitalTwin twin){
		List<GroupFieldModel> list = new ArrayList<GroupFieldModel>();
		for(AssociationTwin ac : twin.getAsociaciones_twin()) {
			if(list.isEmpty() || !list.stream().anyMatch(grupo -> grupo.getGroupCode().equals(ac.getGf().getGrupo().getCodigo()))) {
				GroupFieldModel model = new GroupFieldModel();
				model.setGroupCode(ac.getGf().getGrupo().getCodigo());
				list.add(model);
			}		
		}
		list.forEach((grupo)->{
			grupo.setFields(groupFieldService.getFieldsAndValuesByGroupTwin(grupo.getGroupCode(),twin.getCodigo()));
		});
		return list;
	}

}
