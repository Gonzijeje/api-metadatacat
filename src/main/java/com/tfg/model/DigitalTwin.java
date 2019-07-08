package com.tfg.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 
 * @author gcollada
 *
 */
@Entity
@Table(name = "DIGITAL_TWIN")
public class DigitalTwin extends AbstractBasicoEntity{
	
	@OneToMany(mappedBy = "dt")
	Set<AssociationTwin> asociaciones_twin = new HashSet<>();
	
	@OneToMany(mappedBy = "assets_in")
	Set<DigitalAsset> asociaciones_assets_in = new HashSet<>();
	
	@OneToMany(mappedBy = "assets_out")
	Set<DigitalAsset> asociaciones_assets_out = new HashSet<>();
	
	public DigitalTwin(String codigo, String descripcion) {
		super();
		this.codigo=codigo;
		this.descripcion=descripcion;	
	}
	
	public DigitalTwin() {
		
	}

	public Set<AssociationTwin> getAsociaciones_twin() {
		return asociaciones_twin;
	}

	public void setAsociaciones_twin(Set<AssociationTwin> asociaciones_twin) {
		this.asociaciones_twin = asociaciones_twin;
	}

	public Set<DigitalAsset> getAsociaciones_assets_in() {
		return asociaciones_assets_in;
	}

	public void setAsociaciones_assets_in(Set<DigitalAsset> asociaciones_assets_in) {
		this.asociaciones_assets_in = asociaciones_assets_in;
	}

	public Set<DigitalAsset> getAsociaciones_assets_out() {
		return asociaciones_assets_out;
	}

	public void setAsociaciones_assets_out(Set<DigitalAsset> asociaciones_assets_out) {
		this.asociaciones_assets_out = asociaciones_assets_out;
	}

}
