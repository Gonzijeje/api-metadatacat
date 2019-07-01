package com.tfg.services.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GroupModel {
	
	private String code;
	private String description;

	@JsonProperty("code")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "FieldModel [code=" + code + ", description=" + description + "]";
	}

}
