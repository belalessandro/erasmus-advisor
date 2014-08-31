package it.unipd.dei.bding.erasmusadvisor.beans;

import java.io.Serializable;

/**
* Bean which represents the "Origine" table in the database 
* 
* @author Alessandro
* @version 1.0
*  
*/
public class OrigineBean implements Serializable {
	
	private static final long serialVersionUID = 5015608518668265674L;

	/**
	 * Columns
	 */
	private String idFlusso;
	private int idCorso;

	/**
	 * Empty constructor
	 */
	public OrigineBean() {}

	/**
	 * Returns the field idFlusso.
	 *
	 * @return the value of idFlusso
	 */
	public String getIdFlusso() {
		return idFlusso;
	}

	/**
	 * Sets the field idFlusso
	 *
	 * @param idFlusso The value to set
	 */
	public void setIdFlusso(String idFlusso) {
		this.idFlusso = idFlusso;
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

