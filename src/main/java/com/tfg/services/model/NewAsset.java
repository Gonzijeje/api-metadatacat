package com.tfg.services.model;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NewAsset {
	
	private String name;
	private String code;
	private String description;
	private String owner;
	private String path;
	private Integer size;
	private Date createDate;
	private Date lastModifiedDate;

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
	
	@NotBlank
	@JsonProperty("name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("owner")
	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	@JsonProperty("path")
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@JsonProperty("size")
	@Min(value = 0, message = "Size value must be positive")
	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	@JsonProperty("created")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@JsonProperty("last_modified")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	
	public List<String> getAttributes(){
		List<String> list = new ArrayList<>();
		list.add("name");
		list.add("owner");list.add("path");
		list.add("size");list.add("created");
		list.add("last_modified");
		return list;
	}
	
	public List<String> getValues(){
		Format formatter = new SimpleDateFormat("dd/MM/yyy hh:mm:ss");
		List<String> list = new ArrayList<>();
		list.add(getName());
		list.add(getOwner()); list.add(getPath());
		list.add(String.valueOf(getSize())); list.add(formatter.format(getCreateDate()));
		list.add(formatter.format(getLastModifiedDate()));
		return list;
	}

}
