package com.tfg.services.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FieldValueModel {
	
	private String codigo;
	private String valor;
	
	@JsonProperty("codigo")
	public String getCode() {
		return codigo;
	}
	
	public void setCode(String code) {
		this.codigo = code;
	}
	
	@JsonProperty("valor")
	public String getValue() {
		return valor;
	}
	
	public void setValue(String value) {
		this.valor = value;
	}

	@Override
	public String toString() {
		return "FieldValueModel [code=" + codigo + ", value=" + valor + "]";
	}

}
