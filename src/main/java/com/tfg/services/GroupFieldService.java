package com.tfg.services;

import java.util.List;

import com.tfg.model.GroupField;
import com.tfg.services.model.FieldValueModel;

/**
 * Interfaz de operaciones definidas relacionadas con los GroupFields
 * @author gcollada
 *
 */
public interface GroupFieldService {
	
	void add(GroupField gc);
	
	/**
	 * Método para añadir una lista de GroupFields
	 * @param groupFields lista de los GroupFields a insertar.
	 */
	void addListGroupFields(List<GroupField> groupFields);
	
	/**
	 * Método para eliminar una lista de GroupFields
	 * @param groupFields lista de los GroupFields a eliminar.
	 */
	void deleteListGroupFields(List<GroupField> groupFields);
	
	/**
	 * Método para obtener los campos y valores de metadatos asociados a un grupo
	 * de un DigitalAsset
	 * @param groupCode Código del grupo asociado
	 * @param assetCode Código del DigitalAsset asociado
	 * @return Lista de los modelos JSON de los objetos FieldValue, campo/valor asociados
	 * a dicho grupo y recurso(tipo List<FieldValueModel>)
	 */
	List<FieldValueModel> getFieldsAndValuesByGroupAsset(String groupCode, String assetCode);
	
	/**
	 * Método para obtener los campos y valores de metadatos asociados a un grupo
	 * de un DigitalTwin
	 * @param groupCode Código del grupo asociado
	 * @param twinCode Código del DigitalTwin asociado
	 * @return Lista de los modelos JSON de los objetos FieldValue, campo/valor asociados
	 * a dicho grupo y recuros (tipo List<FieldValueModel>)
	 */
	List<FieldValueModel> getFieldsAndValuesByGroupTwin(String groupCode, String twinCode);

}
