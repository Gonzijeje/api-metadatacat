package com.mrfaces.model.audit;

import java.util.Date;

/**
 * Inteface que han de implementar todas las entidades de dominio que sean auditables.
 *
 */
public interface Auditable {

	/**
	 * Obtiene el usuario de creación.
	 * 
	 * @return el usuario de creación.
	 */
	String getCreateUser();

	/**
	 * Establece el usuario de creación.
	 * 
	 * @param createUser
	 *           el usuario de creación.
	 */
	void setCreateUser(String createUser);

	/**
	 * Obtiene la fecha de creación.
	 * 
	 * @return la fecha de creación.
	 */
	Date getCreateDate();

	/**
	 * Establece la fecha de creación.
	 * 
	 * @param createDate
	 *           la fecha de creación.
	 */
	void setCreateDate(Date createDate);

	/**
	 * Obtiene el usuario de modificación.
	 * 
	 * @return el usuario de modificación.
	 */
	String getUpdateUser();

	/**
	 * Establece el usuario de modificación.
	 * 
	 * @param updateUser
	 *           el usuario de modificación.
	 */
	void setUpdateUser(String updateUser);

	/**
	 * Obtiene la fecha de modificación.
	 * 
	 * @return la fecha de modificación.
	 */
	Date getUpdateDate();

	/**
	 * Establece la fecha de modificación.
	 * 
	 * @param updateDate
	 *           la fecha de modificación.
	 */
	void setUpdateDate(Date updateDate);
}
