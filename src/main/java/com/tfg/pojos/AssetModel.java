package com.tfg.pojos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AssetModel {
	
	private String code;
	private String description;
	private List<GroupFieldModel> grupos;
	
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
	
	@JsonProperty("groups")
	public List<GroupFieldModel> getGrupos() {
		return grupos;
	}
	
	public void setGrupos(List<GroupFieldModel> grupos) {
		this.grupos = grupos;
	}
	
	@Override
	public String toString() {
		return "AssetModel [code=" + code + ", description=" + description + ", grupos=" + grupos + "]";
	}	

}
