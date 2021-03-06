package it.unipd.dei.bding.erasmusadvisor.beans;

import java.io.Serializable;

/**
* Bean which represents the "LinguaTesi" table in the database 
* 
* @author Alessandro
* @version 1.0
*  
*/
public class LinguaTesiBean implements Serializable {
	
	private static final long serialVersionUID = 7283985145983287087L;

	/**
	 * Columns
	 */
	private String siglaLingua;
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

