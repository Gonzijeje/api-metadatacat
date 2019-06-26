package com.tfg.dao;

import org.springframework.data.repository.CrudRepository;

import com.tfg.model.GroupField;
import com.tfg.model.id.Grupo_Campo_Id;

/**
 * 
 * @author gcollada
 *
 */
public interface Grupo_campoRepository extends CrudRepository<GroupField, Grupo_Campo_Id>{
	
	@Query("Select c.codigo,v.valor\r\n" + 
			"from campo c, valor_campo v, grupo_campo gc, grupo g\r\n" + 
			"where c.id = gc.campo_id\r\n" + 
			"and v.id = gc.valor_id\r\n" + 
			"and gc.grupo_id = g.id\r\n" + 
			"and g.codigo  = \"básicos\";")

}
