package com.tfg.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.tfg.model.id.AssociationAssetId;

/**
 * 
 * @author gcollada
 *
 */
@Entity
@Table(name = "AC_ASSET")
public class AssociationAsset {
	
	@EmbeddedId
	AssociationAssetId id;
	
	@ManyToOne
	@MapsId("asset_id")
	@JoinColumn(name="asset_id")
    public DigitalAsset da;
	
	
	@MapsId("groupField_id")
    @JoinColumns({
        @JoinColumn(name="group_id", referencedColumnName="group_id"),
        @JoinColumn(name="field_id", referencedColumnName="field_id"),
        @JoinColumn(name="value_id", referencedColumnName="value_id")
    })
    @ManyToOne
    public GroupField gf;
	

	public AssociationAsset(DigitalAsset da, GroupField groupField) {
		super();
		this.id = new AssociationAssetId(da.getId(),groupField.getId());
		this.da = da;
		this.gf = groupField;
	}
	
	public AssociationAsset() {		
	}

	public DigitalAsset getDa() {
		return da;
	}

	public void setDa(DigitalAsset da) {
		this.da = da;
	}

	public GroupField getGf() {
		return gf;
	}

	public void setGf(GroupField gf) {
		this.gf = gf;
	}
	
}
