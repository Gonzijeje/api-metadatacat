package com.tfg.services.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SimpleAsset {
	
	private Long id;
	private String code;
	
	@JsonIgnore
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@JsonProperty("code")
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}

}
