package com.tfg.services;

import java.util.List;
import java.util.Map;

import com.tfg.model.DigitalAsset;
import com.tfg.services.model.AssetModel;
import com.tfg.services.model.GroupFieldModel;
import com.tfg.services.model.NewAsset;

/**
 * Interfaz de operaciones definidas relacionadas con el recurso Digial Asset
 * @author gcollada
 *
 */
public interface DigitalAssetService{
	
	/**
	 * Método para añadir un Digital Asset al sistema. Comprueba que
	 * no exista ya alguno con el mismo código.
	 * @param newAsset Modelo JSON del nuevo Digital Asset a crear (tipo NewAsset)
	 * @param asset Digital Asset a insertar en la base de datos
	 */
	void add(NewAsset newAsset, DigitalAsset asset);
	
	/**
	 * Método que añade un activo procesado de las canalizaciones de Azure. En vez de comprobar
	 * si ya existe para no permitirlo, se elimina el activo actual y se sutituye por el 
	 * nuevo activo de la canalización. Se utiliza el método flush() para persistir la operación
	 * de borrado antes que la de insertado
	 * @param codigo Código del DigitalAsset que se quiere registrar, para buscar si ya existe y sustituirlo
	 * @param newAsset Modelo de tipo NewAsset con la información obtenida en la obtención automática de metdatos.
	 */
	void addRealAsset(String codigo, NewAsset newAsset);
	
	/**
	 * Método que sirve para obtener la entidad DigitalAsset
	 * a partir del modelo JSON del nuevo Digital Asset
	 * @param newAsset Modelo JSON del nuevo Digital Asset a crear (tipo NewAsset)
	 * @return el Digital Asset creado
	 */
	DigitalAsset create(NewAsset newAsset);
	
	/**
	 * Método que sirve para eliminar un Digital Asset del sistema
	 * @param codigo Código del Digital Asset a eliminar
	 */
	void delete(String codigo);
	
	/**
	 * Método que sirve para encontrar un Digital Asset por su código
	 * @param codigo Código del Digital Asse a buscar
	 * @return el modelo JSON del Digital Asset encontrado (tipo AssetModel)
	 */
	AssetModel findByCodigo(String codigo);
	
	/**
	 * Método para listar todos los Digital Assets del sistema
	 * @return una lista de los modelos JSON de los Assets (tipo List<AssetModel>)
	 */
	List<AssetModel> getDigitalAssets();
	
	/**
	 * Método para buscar los Digital Assets que cumplan con el filtro proporcionado
	 * @param filters Mapa de campos/valores que se corresponden con los filtros
	 * que deben cumplir los Digital Assets a buscar
	 * @return una lista de los modelos JSON de los Assets encontrados (tipo List<AssetModel>)
	 */
	List<AssetModel> getDigitalAssetsByFilters(Map<String,Object> filters);
	
	/**
	 * Método para añadir metadatos a un DigitalAsset.
	 * @param models Lista de modelos JSON con las agrupaciones de metadatos a añadir (tipo List<GroupFieldModel>)
	 * @param code Código del Digital Asset al que se desean asociar los metadatos 
	 */
	void addMetadata(List<GroupFieldModel> models, String code);
	
	/**
	 * Método para desasociar metadatos a un DigitalAsset.
	 * @param models Lista de modelos JSON con las agrupaciones de metadatos a eliminar (tipo List<GroupFieldModel>)
	 * @param code Código del Digital Asset al que se desean elimnar los metadatos 
	 */
	void deleteMetadata(List<GroupFieldModel> models, String code);
	
	/**
	 * Método que comprueba que una lista de Digital Assets existan en el sistema.
	 * @param codes Lista de códigos de los Digital Assets a comprobar
	 * @return true si existen todos.
	 * 			false si no existe alguno.
	 */
	boolean checkListAssets(List<String> codes);

}
