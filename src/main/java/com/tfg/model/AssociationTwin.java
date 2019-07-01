package com.tfg.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.tfg.model.id.AssociationTwinId;

/**
 * 
 * @author gcollada
 *
 */
@Entity
@Table(name = "AC_TWIN")
public class AssociationTwin {
	
	@EmbeddedId
	AssociationTwinId id;
	
	@ManyToOne
	@MapsId("twin_id")
	@JoinColumn(name="twin_id")
	DigitalTwin dt;
	
	@ManyToOne
	@MapsId("ac_grupo_id")
	@JoinColumn(name="ac_grupo_id")
	Group grupo;
	
	@ManyToOne
	@MapsId("ac_campo_id")
	@JoinColumn(name="ac_campo_id")
	Field campo;
	
	@ManyToOne
	@MapsId("ac_value_id")
	@JoinColumn(name="ac_value_id")
	Value value;

	public AssociationTwin(DigitalTwin dt, Group grupo, Field campo, Value value) {
		super();
		this.id = new AssociationTwinId(dt.getId(),grupo.getId(),campo.getId(),value.getId());
		this.dt = dt;
		this.grupo = grupo;
		this.campo = campo;
		this.value = value;
	}
	
	public AssociationTwin() {		
	}

	public DigitalTwin getDt() {
		return dt;
	}

	public void setDt(DigitalTwin dt) {
		this.dt = dt;
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
		return value;
	}

	public void setValue(Value value) {
		this.value = value;
	}

}
