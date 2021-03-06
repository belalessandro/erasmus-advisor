package it.unipd.dei.bding.erasmusadvisor.beans;

import java.io.Serializable;

/**
* Bean which represents the "Interesse" table in the database 
* 
* @author Alessandro
* @version 1.0
*  
*/
public class InteresseBean implements Serializable {
	
	private static final long serialVersionUID = -4515163755338507477L;

	/**
	 * Columns
	 */
	private String nomeUtenteStudente;
	private String idFlusso;

	/**
	 * Empty constructor
	 */
	public InteresseBean() {}

	/**
	 * Returns the field nomeUtenteStudente.
	 *
	 * @return the value of nomeUtenteStudente
	 */
	public String getNomeUtenteStudente() {
		return nomeUtenteStudente;
	}

	/**
	 * Sets the field nomeUtenteStudente
	 *
	 * @param nomeUtenteStudente The value to set
	 */
	public void setNomeUtenteStudente(String nomeUtenteStudente) {
		this.nomeUtenteStudente = nomeUtenteStudente;
	}

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
}

