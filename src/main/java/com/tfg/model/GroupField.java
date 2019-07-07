package com.tfg.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import com.tfg.model.id.GroupFieldId;

/**
 * 
 * @author gcollada
 *
 */
@Entity
@Table(name = "GROUP_FIELD")
public class GroupField {
	
	@EmbeddedId
	GroupFieldId id;
	
	@ManyToOne
	@MapsId("group_id")
	@JoinColumn(name="group_id")
	Group grupo;
	
	@ManyToOne
	@MapsId("field_id")
	@JoinColumn(name="field_id")
	Field campo;
	
	@ManyToOne
	@MapsId("value_id")
	@JoinColumn(name="value_id")
	Value valor;		

	public GroupField(Group grupo, Field campo, Value value) {
		super();
		this.id = new GroupFieldId(grupo.getId(), campo.getId(), value.getId());
		this.grupo = grupo;
		this.campo = campo;
		this.valor = value;
	}
	
	public GroupField() {	
	}

	public Group getGrupo() {
		return grupo;
	}

	public void setGrupo(Group grupo) {
		this.grupo = grupo;
	}

	public Field getCampo() {
		return campo;
	}

	public void setCampo(Field campo) {
		this.campo = campo;
	}

	public Value getValue() {
		return valor;
	}

	public void setValue(Value value) {
		this.valor = value;
	}

}
