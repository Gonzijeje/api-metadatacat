package com.tfg.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.tfg.model.id.Ac_Twin_Id;

/**
 * 
 * @author gcollada
 *
 */
@Entity
@Table(name = "AC_TWIN")
public class Ac_Twin {
	
	@EmbeddedId
	Ac_Twin_Id id;
	
	@ManyToOne
	@MapsId("twin_id")
	@JoinColumn(name="twin_id")
	DigitalTwin dt;
	
	@ManyToOne
	@MapsId("ac_grupo_id")
	@JoinColumn(name="ac_grupo_id")
	Grupo grupo;
	
	@ManyToOne
	@MapsId("ac_campo_id")
	@JoinColumn(name="ac_campo_id")
	Campo campo;

	public Ac_Twin(DigitalTwin dt, Grupo grupo, Campo campo) {
		super();
		this.id = new Ac_Twin_Id(dt.getId(),grupo.getId(),campo.getId());
		this.dt = dt;
		this.grupo = grupo;
		this.campo = campo;
	}
	
	public Ac_Twin() {		
	}

	public DigitalTwin getDt() {
		return dt;
	}

	public void setDt(DigitalTwin dt) {
		this.dt = dt;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public Campo getCampo() {
		return campo;
	}

	public void setCampo(Campo campo) {
		this.campo = campo;
	}

}
