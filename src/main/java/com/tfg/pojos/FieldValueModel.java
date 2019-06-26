package com.tfg.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FieldValueModel {
	
	private String code;
	private String value;
	
	@JsonProperty("field_code")
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	@JsonProperty("value")
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "FieldValueModel [code=" + code + ", value=" + value + "]";
	}

}
