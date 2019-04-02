package com.tfg.model;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.mrfaces.model.AbstractGeneratedIdAuditableEntity;


/**
 * Clase base de la que derivar entidades basicas.
 * 
 * @author diegogr
 *
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractBasicoEntity extends AbstractGeneratedIdAuditableEntity {

	/**
	 * Serializable.
	 */
	private static final long serialVersionUID = 2456121915872267904L;

	@NotNull(message = "{codigo.obligatorio}")
	@Size(max = 40, message = "{codigo.longitud.maxima}")
	@Column(name = "CODIGO", nullable = false, length = 40)
	protected String codigo;

	@Size(max = 350, message = "{descripcion.longitud.maxima}")
	@Column(name = "DESCRIPCION", nullable = true, length = 80)
	protected String descripcion;
	

	/**
	 * Método get de la propiedad código.
	 * 
	 * @return codigo.
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * Método set de la propiedad código.
	 * 
	 * @param codigo
	 *           tipo string.
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * Método get de la propiedad descripción.
	 * 
	 * @return descripcion.
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Método set de la propiedad descripción.
	 * 
	 * @param descripcion
	 *           tipo string.
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
