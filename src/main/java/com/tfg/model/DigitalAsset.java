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
@Table(name = "DIGITAL_ASSET")
public class DigitalAsset extends AbstractBasicoEntity{
	
	@OneToMany(mappedBy = "da")
	Set<Ac_Asset> asociaciones_asset = new HashSet<>();
		
	
	public DigitalAsset(String codigo, String descripcion) {
		super();
		this.codigo=codigo;
		this.descripcion=descripcion;
	}
	
	public DigitalAsset() {
		
	}

	public Set<Ac_Asset> getAsociaciones_asset() {
		return asociaciones_asset;
	}

	public void setAsociaciones_asset(Set<Ac_Asset> asociaciones_asset) {
		this.asociaciones_asset = asociaciones_asset;
	}

}
