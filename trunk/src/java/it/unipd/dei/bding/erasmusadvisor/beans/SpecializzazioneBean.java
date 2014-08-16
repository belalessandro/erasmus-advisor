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
class SpecializzazioneBean implements Serializable {
	/**
	 *
	 */
	private String nomeArea;

	/**
	 *
	 */
	private int idCorso;

	/**
	 * Empty constructor
	 */
	public SpecializzazioneBean() {}

	/**
	 * Returns the field nomeArea.
	 *
	 * @return the value of nomeArea
	 */
	public String getNomeArea() {
		return nomeArea;
	}

	/**
	 * Sets the field nomeArea
	 *
	 * @param nomeArea The value to set
	 */
	public void setNomeArea(String nomeArea) {
		this.nomeArea = nomeArea;
	}

	/**
	 * Returns the field idCorso.
	 *
	 * @return the value of idCorso
	 */
	public int getIdCorso() {
		return idCorso;
	}

	/**
	 * Sets the field idCorso
	 *
	 * @param idCorso The value to set
	 */
	public void setIdCorso(int idCorso) {
		this.idCorso = idCorso;
	}
}

