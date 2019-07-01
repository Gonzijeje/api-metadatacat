package com.tfg.services.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NewGroup {
	
	private String code;
	private String description;

	@NotBlank @Size(max = 40, message = "{codigo.longitud.maxima}")
	@JsonProperty("code")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@NotNull @Size(max = 350, message = "{descripcion.longitud.maxima}")
	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "NewField [code=" + code + ", description=" + description + "]";
	}

}
