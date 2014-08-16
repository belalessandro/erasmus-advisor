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
class LinguaTesiBean implements Serializable {
	/**
	 *
	 */
	private String siglaLingua;

	/**
	 *
	 */
	private int idArgomentoTesi;

	/**
	 * Empty constructor
	 */
	public LinguaTesiBean() {}

	/**
	 * Returns the field siglaLingua.
	 *
	 * @return the value of siglaLingua
	 */
	public String getSiglaLingua() {
		return siglaLingua;
	}

	/**
	 * Sets the field siglaLingua
	 *
	 * @param siglaLingua The value to set
	 */
	public void setSiglaLingua(String siglaLingua) {
		this.siglaLingua = siglaLingua;
	}

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
}

