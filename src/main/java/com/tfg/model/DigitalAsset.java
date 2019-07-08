package com.tfg.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 
 * @author gcollada
 *
 */
@Entity
@Table(name = "DIGITAL_ASSET")
public class DigitalAsset extends AbstractBasicoEntity{
	
	@OneToMany(mappedBy = "da")
	Set<AssociationAsset> asociaciones_asset = new HashSet<>();
	
	@ManyToOne
	@JoinColumn(name="assets_in")
	DigitalTwin assets_in;
	
	@ManyToOne
	@JoinColumn(name="assets_out")
	DigitalTwin assets_out;
		
	
	public DigitalAsset(String codigo, String descripcion) {
		super();
		this.codigo=codigo;
		this.descripcion=descripcion;
	}
	
	public DigitalAsset() {
		
	}

	public Set<AssociationAsset> getAsociaciones_asset() {
		return asociaciones_asset;
	}

	public void setAsociaciones_asset(Set<AssociationAsset> asociaciones_asset) {
		this.asociaciones_asset = asociaciones_asset;
	}

	public DigitalTwin getAssets_in() {
		return assets_in;
	}

	public void setAssets_in(DigitalTwin assets_in) {
		this.assets_in = assets_in;
	}

	public DigitalTwin getAssets_out() {
		return assets_out;
	}

	public void setAssets_out(DigitalTwin assets_out) {
		this.assets_out = assets_out;
	}

}
