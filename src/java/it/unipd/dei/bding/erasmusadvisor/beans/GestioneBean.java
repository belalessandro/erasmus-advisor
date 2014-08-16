package it.unipd.dei.bding.erasmusadvisor.beans;

import java.io.Serializable;
import java.sql.Date;

/**
 * Represents the ...
 * 
 * @author Alessandro
 * @version 1.0
 *  
 */
class GestioneBean implements Serializable {
	/**
	 *
	 */
	private int idArgomentoTesi;

	/**
	 *
	 */
	private int idProfessore;

	/**
	 * Empty constructor
	 */
	public GestioneBean() {}

	/**
	 * Returns the field idArgomentoTesi.
	 *
	 * @return the value of idArgomentoTesi
	 */
	public int getIdArgomentoTesi() {
		return idArgomentoTesi;
	}

	/**
	 * Sets the field idArgomentoTesi
	 *
	 * @param idArgomentoTesi The value to set
	 */
	public void setIdArgomentoTesi(int idArgomentoTesi) {
		this.idArgomentoTesi = idArgomentoTesi;
	}

	/**
	 * Returns the field idProfessore.
	 *
	 * @return the value of idProfessore
	 */
	public int getIdProfessore() {
		return idProfessore;
	}

	/**
	 * Sets the field idProfessore
	 *
	 * @param idProfessore The value to set
	 */
	public void setIdProfessore(int idProfessore) {
		this.idProfessore = idProfessore;
	}
}

