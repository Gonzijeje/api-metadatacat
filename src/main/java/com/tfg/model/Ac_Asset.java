package com.tfg.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.tfg.model.id.Ac_Asset_Id;

/**
 * 
 * @author gcollada
 *
 */
@Entity
@Table(name = "AC_ASSET")
public class Ac_Asset {
	
	@EmbeddedId
	Ac_Asset_Id id;
	
	@ManyToOne
	@MapsId("asset_id")
	@JoinColumn(name="asset_id")
	DigitalAsset da;
	
	@ManyToOne
	@MapsId("ac_grupo_id")
	@JoinColumn(name="ac_grupo_id")
	Group grupo;
	
	@ManyToOne
	@MapsId("ac_campo_id")
	@JoinColumn(name="ac_campo_id")
	Field campo;

	public Ac_Asset(DigitalAsset da, Group grupo, Field campo) {
		super();
		this.id = new Ac_Asset_Id(da.getId(),grupo.getId(),campo.getId());
		this.da = da;
		this.grupo = grupo;
		this.campo = campo;
	}
	
	public Ac_Asset() {		
	}

	public DigitalAsset getDa() {
		return da;
	}

	public void setDa(DigitalAsset da) {
		this.da = da;
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

}
