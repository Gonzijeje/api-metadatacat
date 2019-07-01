package com.tfg.services.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TwinModel {
	
	private Long id;
	
	private String code;
	private String description;
	private List<SimpleAsset> assetsIn;
	private List<GroupFieldModel> grupos;
	private List<SimpleAsset> assetsOut;
	
	
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

	@JsonProperty("assetsIn")
	public List<SimpleAsset> getAssetsIn() {
		return assetsIn;
	}

	public void setAssetsIn(List<SimpleAsset> assetsIn) {
		this.assetsIn = assetsIn;
	}

	@JsonProperty("assetsOut")
	public List<SimpleAsset> getAssetsOut() {
		return assetsOut;
	}

	public void setAssetsOut(List<SimpleAsset> assetsOut) {
		this.assetsOut = assetsOut;
	}

}
