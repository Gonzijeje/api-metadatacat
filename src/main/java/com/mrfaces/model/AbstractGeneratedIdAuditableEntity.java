package com.mrfaces.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.mrfaces.model.audit.Auditable;


/**
 * Clase base de la que derivar entidades auditables con ID con valor autogenerado.
 * 
 * @author jromero
 *
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractGeneratedIdAuditableEntity extends AbstractGeneratedIdEntity implements Auditable {

	/**
	 * Serializable.
	 */
	private static final long serialVersionUID = 4848044111277219918L;

	/**
	 * Usuario de creacion.
	 */
	@NotBlank
	@Column(name = "CREATE_USER", length = 40)
	@CreatedBy
	private String createUser;

	/**
	 * Fecha de creación.
	 */
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_DATE")
	@CreatedDate
	private Date createDate;

	/**
	 * Usuario de modificación.
	 */
	@Column(name = "UPDATE_USER", length = 40)
	@LastModifiedBy
	private String updateUser;

	/**
	 * Fecha de modificación.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATE_DATE")
	@LastModifiedDate
	private Date updateDate;

	@Override
	public String getCreateUser() {
		return createUser;
	}

	@Override
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	@Override
	public Date getCreateDate() {
		return createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public String getUpdateUser() {
		return updateUser;
	}

	@Override
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	@Override
	public Date getUpdateDate() {
		return updateDate;
	}

	@Override
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
}
