package com.tfg.services.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FieldValueModel {
	
	private String codigo;
	private String valor;
	
	@JsonProperty("field_code")
	public String getCode() {
		return codigo;
	}
	
	public void setCode(String code) {
		this.codigo = code;
	}
	
	@JsonProperty("value")
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
