package com.tfg.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
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
	
	@NotNull(message = "{extension.obligatorio}")
	@Size(max = 40, message = "{extension.longitud.maxima}")
	@Column(name = "EXTENSION", nullable = false, length = 40)
	protected String extension;
	
	@NotNull(message = "{entidad.obligatorio}")
	@Size(max = 40, message = "{entidad.longitud.maxima}")
	@Column(name = "ENTIDAD", nullable = false, length = 40)
	protected String entidad;
	
	@NotNull(message = "{contacto.obligatorio}")
	@Size(max = 40, message = "{contacto.longitud.maxima}")
	@Column(name = "CONTACTO", nullable = true, length = 40)
	protected String contacto;
	
	@NotNull(message = "{autor.obligatorio}")
	@Size(max = 40, message = "{autor.longitud.maxima}")
	@Column(name = "AUTOR", nullable = true, length = 40)
	protected String autor;
	
	@NotNull(message = "{tamano.obligatorio}")
	@Size(max = 5, message = "{tamano.longitud.maxima}")
	@Column(name = "TAMANO", nullable = false, length = 40)
	protected double tamano;
	
	@NotNull(message = "{unidad_tamano.obligatorio}")
	@Size(max = 10, message = "{unidad_tamano.longitud.maxima}")
	@Column(name = "UNIDAD_TAMANO", nullable = false, length = 40)
	protected String unidad_tamano;
	
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
		
	
	public DigitalTwin(String codigo, String descripcion, String extension,String entidad,String contacto,String autor,double tamano,String unidad_tamano,
			String path,Date fecha_creacion,Date fecha_modificacion,String entorno) {
		super();
		super.codigo=codigo;
		super.descripcion=descripcion;
		this.extension = extension;
		this.entidad = entidad;
		this.contacto = contacto;
		this.autor = autor;
		this.tamano = tamano;
		this.unidad_tamano = unidad_tamano;
		this.path = path;
		this.fecha_creacion = fecha_creacion;
		this.fecha_modificacion = fecha_modificacion;
		this.entorno=entorno;
	}
	
	public DigitalTwin() {
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getEntidad() {
		return entidad;
	}

	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}

	public String getContacto() {
		return contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public double getTamano() {
		return tamano;
	}

	public void setTamano(double tamano) {
		this.tamano = tamano;
	}

	public String getUnidad_tamano() {
		return unidad_tamano;
	}

	public void setUnidad_tamano(String unidad_tamano) {
		this.unidad_tamano = unidad_tamano;
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
