package com.tfg.model;

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
	Set<Ac_Twin> asociaciones_twin;
	
	@OneToMany(mappedBy = "assets_in")
	Set<DigitalAsset> asociaciones_assets_in;
	
	@OneToMany(mappedBy = "assets_out")
	Set<DigitalAsset> asociaciones_assets_out;
	
	public DigitalTwin(String codigo, String descripcion) {
		super();
		this.codigo=codigo;
		this.descripcion=descripcion;	
	}
	
	public DigitalTwin() {
		
	}

	public Set<Ac_Twin> getAsociaciones_twin() {
		return asociaciones_twin;
	}

	public void setAsociaciones_twin(Set<Ac_Twin> asociaciones_twin) {
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
