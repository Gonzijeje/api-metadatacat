package com.tfg.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.tfg.model.id.AssociationAssetId;

/**
 * 
 * @author gcollada
 *
 */
@Entity
@Table(name = "AC_ASSET")
public class AssociationAsset {
	
	@EmbeddedId
	AssociationAssetId id;
	
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
	
	@ManyToOne
	@MapsId("ac_value_id")
	@JoinColumn(name="ac_value_id")
	Value value;

	public AssociationAsset(DigitalAsset da, Group grupo, Field campo, Value value) {
		super();
		this.id = new AssociationAssetId(da.getId(),grupo.getId(),campo.getId(),value.getId());
		this.da = da;
		this.grupo = grupo;
		this.campo = campo;
		this.value = value;
	}
	
	public AssociationAsset() {		
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

	public Value getValue() {
		return value;
	}

	public void setValue(Value value) {
		this.value = value;
	}

}
