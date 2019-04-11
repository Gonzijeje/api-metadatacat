package com.tfg.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import com.tfg.model.id.Grupo_Campo_Id;

/**
 * 
 * @author gcollada
 *
 */
@Entity
@Table(name = "GRUPO_CAMPO")
public class Grupo_campo {
	
	@EmbeddedId
	Grupo_Campo_Id id;
	
	@ManyToOne
	@MapsId("grupo_id")
	@JoinColumn(name="grupo_id")
	Grupo grupo;
	
	@ManyToOne
	@MapsId("campo_id")
	@JoinColumn(name="campo_id")
	Campo campo;
	
	@ManyToOne
	@MapsId("valor_id")
	@JoinColumn(name="valor_id")
	Valor_Campo valor;
	
	/*@NotNull(message = "{valor.obligatorio}")
	@Size(max = 40, message = "{valor.longitud.maxima}")
	@Column(name = "VALOR", nullable = false, length = 40)
	String valor;*/
	
	
	/*@ManyToMany
	@JoinTable(
			name = "AC_TWIN",
			joinColumns = {@JoinColumn(name = "ac_grupo_id", referencedColumnName = "grupo_id"),
							@JoinColumn(name = "ac_campo_id", referencedColumnName = "campo_id")},
			inverseJoinColumns = @JoinColumn(name = "twin_id"))*/
	/*@OneToMany
	Set<DigitalTwin> twinsAsociados;*/

	public Grupo_campo(Grupo grupo, Campo campo, Valor_Campo value) {
		super();
		this.id = new Grupo_Campo_Id(grupo.getId(), campo.getId(), value.getId());
		this.grupo = grupo;
		this.campo = campo;
		this.valor = value;
	}
	
	public Grupo_campo() {	
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public Campo getCampo() {
		return campo;
	}

	public void setCampo(Campo campo) {
		this.campo = campo;
	}

	public Valor_Campo getValue() {
		return valor;
	}

	public void setValue(Valor_Campo value) {
		this.valor = value;
	}

}
