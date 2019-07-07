package com.tfg.services.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PipelineModel {

	private String name;
	private String datasetInput;
	
	@NotBlank  @Size(max = 50)
	@JsonProperty("name")
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@JsonProperty("dataset_input")
	public String getDatasetInput() {
		return datasetInput;
	}
	
	public void setDatasetInput(String datasetInput) {
		this.datasetInput = datasetInput;
	}
	
	
}
