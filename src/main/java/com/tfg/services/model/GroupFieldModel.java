package com.tfg.services.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GroupFieldModel {
	
	private String groupCode;
	private List<FieldValueModel> fields;
	
	@JsonProperty("group_code")
	public String getGroupCode() {
		return groupCode;
	}
	
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	
	@JsonProperty("fields")
	public List<FieldValueModel> getFields() {
		return fields;
	}
	
	public void setFields(List<FieldValueModel> fields) {
		this.fields = fields;
	}

	@Override
	public String toString() {
		return "GroupFieldModel [groupCode=" + groupCode + ", fields=" + fields + "]";
	}
	
	@JsonIgnore
	public List<String> getFieldCodes(){
		List<String> list = new ArrayList<String>();
		fields.forEach((field)->{
			list.add(field.getCode());
		});
		return list;
	}
	
	@JsonIgnore
	public List<String> getValues(){
		List<String> list = new ArrayList<String>();
		fields.forEach((field)->{
			list.add(field.getValue());
		});
		return list;
	}
}
