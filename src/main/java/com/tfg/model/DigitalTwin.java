package com.tfg.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 
 * @author gcollada
 *
 */
@Entity
@Table(name = "DIGITAL_TWIN")
public class DigitalTwin extends AbstractBasicoEntity{

	/**
	 * Serializable.
	 */
	private static final long serialVersionUID = 5277192833345636618L;
	
	@NotNull(message = "{tipo.obligatorio}")
	@Size(max = 40, message = "{tipo.longitud.maxima}")
	@Column(name = "TIPO", nullable = false, length = 40)
	protected String tipo;
	
	@NotNull(message = "{entidad.obligatorio}")
	@Size(max = 40, message = "{entidad.longitud.maxima}")
	@Column(name = "ENTIDAD", nullable = false, length = 40)
	protected String entidad;
	
	@NotNull(message = "{autor.obligatorio}")
	@Size(max = 40, message = "{autor.longitud.maxima}")
	@Column(name = "AUTOR", nullable = true, length = 40)
	protected String autor;
	
	@NotNull(message = "{path.obligatorio}")
	@Size(max = 40, message = "{path.longitud.maxima}")
	@Column(name = "PATH", nullable = false, length = 40)
	protected String path;
	
	@NotNull(message = "{fecha_creacion.obligatorio}")
	@Size(max = 20, message = "{fecha_creacion.longitud.maxima}")
	@Column(name = "FECHA_CREACION", nullable = false, length = 40)
	protected Date fecha_creacion;
	
	@Size(max = 20, message = "{fecha_modificacion.longitud.maxima}")
	@Column(name = "FECHA_MODIFICACION", nullable = false, length = 40)
	protected Date fecha_modificacion;
	
	@NotNull(message = "{entorno.obligatorio}")
	@Size(max = 100, message = "{entorno.longitud.maxima}")
	@Column(name = "ENTORNO", nullable = false, length = 100)
	protected String entorno;
	
	/*@ManyToMany(mappedBy = "twinsAsociados")
	Set<Grupo_campo> asociaciones_twin;*/
		
	
	public DigitalTwin(String codigo, String descripcion, String extension,String entidad,String autor,
			String path,Date fecha_creacion,Date fecha_modificacion,String entorno) {
		super();
		super.codigo=codigo;
		super.descripcion=descripcion;
		this.tipo = extension;
		this.entidad = entidad;
		this.autor = autor;
		this.path = path;
		this.fecha_creacion = fecha_creacion;
		this.fecha_modificacion = fecha_modificacion;
		this.entorno=entorno;
	}
	
	public DigitalTwin() {
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String extension) {
		this.tipo = extension;
	}

	public String getEntidad() {
		return entidad;
	}

	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Date getFecha_creacion() {
		return fecha_creacion;
	}

	public void setFecha_creacion(Date fecha_creacion) {
		this.fecha_creacion = fecha_creacion;
	}

	public Date getFecha_modificacion() {
		return fecha_modificacion;
	}

	public void setFecha_modificacion(Date fecha_modificacion) {
		this.fecha_modificacion = fecha_modificacion;
	}

	public String getEntorno() {
		return entorno;
	}

	public void setEntorno(String entorno) {
		this.entorno = entorno;
	}

}
