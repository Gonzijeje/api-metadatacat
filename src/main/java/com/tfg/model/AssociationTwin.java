package com.tfg.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.tfg.model.id.AssociationTwinId;

/**
 * 
 * @author gcollada
 *
 */
@Entity
@Table(name = "AC_TWIN")
public class AssociationTwin {
	
	@EmbeddedId
	AssociationTwinId id;
	
	@ManyToOne
	@MapsId("twin_id")
	@JoinColumn(name="twin_id")
	DigitalTwin dt;
	
	@MapsId("groupField_id")
    @JoinColumns({
        @JoinColumn(name="group_id", referencedColumnName="group_id"),
        @JoinColumn(name="field_id", referencedColumnName="field_id"),
        @JoinColumn(name="value_id", referencedColumnName="value_id")
    })
    @ManyToOne
    public GroupField gf;

	public AssociationTwin(DigitalTwin dt, GroupField groupField) {
		super();
		this.id = new AssociationTwinId(dt.getId(),groupField.getId());
		this.dt = dt;
		this.gf = groupField;
	}
	
	public AssociationTwin() {		
	}

	public DigitalTwin getDt() {
		return dt;
	}

	public void setDt(DigitalTwin dt) {
		this.dt = dt;
	}

	public GroupField getGf() {
		return gf;
	}

	public void setGf(GroupField gf) {
		this.gf = gf;
	}

}
