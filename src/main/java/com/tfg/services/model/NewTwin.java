package com.tfg.services.model;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NewTwin {
	
	private String code;
	private String description;
	private String environment;
	private String path;
	private Date created;
	private Date lastModifiedDate;
	private String model;
	private List<SimpleAsset> assetsIn;
	private List<SimpleAsset> assetsOut;
	
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
	
	@JsonProperty("environment")
	public String getEnvironment() {
		return environment;
	}
	
	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	
	@JsonProperty("path")
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	@JsonProperty("created")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
	public Date getCreated() {
		return created;
	}
	
	public void setCreated(Date created) {
		this.created = created;
	}
	
	@JsonProperty("last_modified")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	
	@JsonProperty("model")
	public String getModel() {
		return model;
	}
	
	public void setModel(String model) {
		this.model = model;
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

	public List<String> getAttributes(){
		List<String> list = new ArrayList<>();
		list.add("environment"); list.add("path");
		list.add("created"); list.add("last_modified");
		list.add("model");
		return list;
	}
	
	public List<String> getValues(){
		Format formatter = new SimpleDateFormat("dd/MM/yyy hh:mm:ss");
		List<String> list = new ArrayList<>();
		list.add(getEnvironment()); list.add(getPath());
		list.add(formatter.format(getCreated()));
		list.add(formatter.format(getLastModifiedDate()));	
		list.add(getModel());
		return list;
	}
	
}
