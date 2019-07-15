package com.tfg.services;

import java.util.List;

import com.tfg.model.Group;
import com.tfg.services.model.GroupModel;
import com.tfg.services.model.NewGroup;

/**
 * Interfaz de operaciones definidas relacionadas con el recurso Digital Twin
 * @author gcollada
 *
 */
public interface GroupService {
	
	/**
	 * Método para añadir un nuevo Group al sistema. Comprueba
	 * que no exista ya uno con el mismo código.
	 * @param group El objeto Group a insertar
	 * @return modelo JSON del objeto Group creado (tipo GroupModel)
	 */
	GroupModel add(Group group);
	
	/**
	 * Método para modficiar un Group del sistema.
	 * @param code Código del Group a modificar. Debe de existir en el sistema
	 * @param group Objeto Group con la información a modificar. El código no puede
	 * estar repetido salvo que sea el mismo sin modificar.
	 * @return modelo JSON del objeto Group modificado (tipo GroupModel)
	 */
	GroupModel update(String code, Group group);
	
	/**
	 * Método que sirve para obtener la entidad Group
	 * a partir del modelo JSON del nuevo Group
	 * @param newGroup Modelo JSON del nuevo Group a crear (tipo NewGroup)
	 * @return el Digital Asset creado
	 */
	Group create(NewGroup newGroup);
	
	/**
	 * Método que sirve para eliminar un Group del sistema
	 * @param codigo Código del Group a eliminar
	 */
	void delete(String code);
	
	/**
	 * Método para recuperar un Group del sistema
	 * @param code Código del Group que se desea recuperar. Debe de existir en el sistema.
	 * @return El objeto Group recuperado.
	 */
	Group getGroupByCode(String code);
	
	/**
	 * Método para recuperar todos los Groups del sistema.
	 * @return Lista con los modelos JSON de los Groups (tipo List<GroupModel>)
	 */
	List<GroupModel> getGroups();
	
	/**
	 * Método para añadir una lista de Groups
	 * @param groups Lista de los Groups a insertar
	 */
	void addListGroups(List<Group> groups);
	
	/**
	 * Método que comprueba que una lista de Groups existan en el sistema.
	 * @param groups Lista de códigos de los Groups a comprobar
	 * @return true si existen todos.
	 * 			false si no existe alguno.
	 */
	boolean checkListGroups(List<String> groups);

}
