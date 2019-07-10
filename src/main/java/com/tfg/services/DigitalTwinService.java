package com.tfg.services;

import java.util.List;
import java.util.Map;

import com.tfg.model.DigitalTwin;
import com.tfg.services.model.GroupFieldModel;
import com.tfg.services.model.NewTwin;
import com.tfg.services.model.TwinModel;

/**
 * Interfaz de operaciones definidas relacionadas con el recurso Digital Twin
 * @author gcollada
 *
 */
public interface DigitalTwinService{
	
	/**
	 * Método para añadir un Digital Twin al sistema. Comprueba que
	 * no exista ya alguno con el mismo código.
	 * @param newTwin Modelo JSON del nuevo Digital Twin a crear (tipo NewTwin)
	 * @param dt Digital Twin a insertar en la base de datos
	 */
	void add(NewTwin newTwin, DigitalTwin dt);

	/**
	 * Método que sirve para obtener la entidad DigitalTwin
	 * a partir del modelo JSON del nuevo Digital Twin
	 * @param newTwin Modelo JSON del nuevo Digital Twin a crear (tipo NewTwin)
	 * @return el Digital Twin creado
	 */
	DigitalTwin create(NewTwin newTwin);

	/**
	 * Método que sirve para eliminar un Digital Twin del sistema
	 * @param codigo Código del Digital Twin a eliminar
	 */
	void delete(String codigo);

	/**
	 * Método que sirve para encontrar un Digital Twin por su código
	 * @param codigo Código del Digital Twin a buscar
	 * @return el modelo JSON del Digital Twin encontrado (tipo TwinModel)
	 */
	TwinModel findByCodigo(String codigo);

	/**
	 * Método para listar todos los Digital Twins del sistema
	 * @return una lista de los modelos JSON de los Twina (tipo List<TwinModel>)
	 */
	List<TwinModel> getDigitalTwins();

	/**
	 * Método para buscar los Digital Twins que cumplan con el filtro proporcionado
	 * @param filters Mapa de campos/valores que se corresponden con los filtros
	 * que deben cumplir los Digital Twins a buscar
	 * @return una lista de los modelos JSON de los Tins encontrados (tipo List<TwinModel>)
	 */
	List<TwinModel> getDigitalTwinsByFilters(Map<String,Object> filters);

	/**
	 * Método para añadir metadatos a un DigitalTwin.
	 * @param models Lista de modelos JSON con las agrupaciones de metadatos a añadir (tipo List<GroupFieldModel>)
	 * @param code Código del Digital Twin al que se desean asociar los metadatos 
	 */
	void addMetadata(List<GroupFieldModel> models, String code);

	/**
	 * Método para desasociar metadatos a un DigitalTwin.
	 * @param models Lista de modelos JSON con las agrupaciones de metadatos a eliminar (tipo List<GroupFieldModel>)
	 * @param code Código del Digital Twin al que se desean elimnar los metadatos 
	 */
	void deleteMetadata(List<GroupFieldModel> models, String code);
	
}
