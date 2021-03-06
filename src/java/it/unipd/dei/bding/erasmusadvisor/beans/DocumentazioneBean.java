package it.unipd.dei.bding.erasmusadvisor.beans;

import java.io.Serializable;

/**
* Bean which represents the "Documentazione" table in the database 
* 
* @author Alessandro
* @version 1.0
*  
*/
public class DocumentazioneBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1002507782392745327L;

	/**
	 *
	 */
	private String idFlusso;

	/**
	 *
	 */
	private String nomeCertificato;

	/**
	 *
	 */
	private String livelloCertificato;

	/**
	 * Empty constructor
	 */
	public DocumentazioneBean() {}

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
	 * Returns the field nomeCertificato.
	 *
	 * @return the value of nomeCertificato
	 */
	public String getNomeCertificato() {
		return nomeCertificato;
	}

	/**
	 * Sets the field nomeCertificato
	 *
	 * @param nomeCertificato The value to set
	 */
	public void setNomeCertificato(String nomeCertificato) {
		this.nomeCertificato = nomeCertificato;
	}

	/**
	 * Returns the field livelloCertificato.
	 *
	 * @return the value of livelloCertificato
	 */
	public String getLivelloCertificato() {
		return livelloCertificato;
	}

	/**
	 * Sets the field livelloCertificato
	 *
	 * @param livelloCertificato The value to set
	 */
	public void setLivelloCertificato(String livelloCertificato) {
		this.livelloCertificato = livelloCertificato;
	}
}

