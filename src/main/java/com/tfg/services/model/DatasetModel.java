package com.tfg.services.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DatasetModel {
	
	private String name;
	private String fileName;
	private String dataSource;
	
	@NotBlank  @Size(max = 50)
	@JsonProperty("name")
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@JsonProperty("file_name")
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	@JsonProperty("data_source")
	public String getDataSource() {
		return dataSource;
	}
	
	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

}
