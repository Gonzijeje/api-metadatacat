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

}
