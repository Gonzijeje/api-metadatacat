package com.tfg.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import com.tfg.model.id.Grupo_Campo_Id;

/**
 * 
 * @author gcollada
 *
 */
@Entity
@Table(name = "GRUPO_CAMPO")
public class GroupField {
	
	@EmbeddedId
	Grupo_Campo_Id id;
	
	@ManyToOne
	@MapsId("grupo_id")
	@JoinColumn(name="grupo_id")
	Group grupo;
	
	@ManyToOne
	@MapsId("campo_id")
	@JoinColumn(name="campo_id")
	Field campo;
	
	@ManyToOne
	@MapsId("valor_id")
	@JoinColumn(name="valor_id")
	Value valor;		

	public GroupField(Group grupo, Field campo, Value value) {
		super();
		this.id = new Grupo_Campo_Id(grupo.getId(), campo.getId(), value.getId());
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
