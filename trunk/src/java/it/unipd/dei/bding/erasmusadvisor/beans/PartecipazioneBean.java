package it.unipd.dei.bding.erasmusadvisor.beans;

import java.io.Serializable;
import java.sql.Date;

/**
* Bean which represents the "Partecipazione" table in the database 
* 
* @author Alessandro
* @version 1.0
*  
*/
public class PartecipazioneBean implements Serializable {
	
	private static final long serialVersionUID = -4929346344491070921L;

	/**
	 * Columns
	 */
	private String nomeUtenteStudente;
	private String idFlusso;
	private Date inizio;
	private Date fine;

	/**
	 * Empty constructor
	 */
	public PartecipazioneBean() {}

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

	/**
	 * Returns the field inizio.
	 *
	 * @return the value of inizio
	 */
	public Date getInizio() {
		return inizio;
	}

	/**
	 * Sets the field inizio
	 *
	 * @param inizio The value to set
	 */
	public void setInizio(Date inizio) {
		this.inizio = inizio;
	}

	/**
	 * Returns the field fine.
	 *
	 * @return the value of fine
	 */
	public Date getFine() {
		return fine;
	}

	/**
	 * Sets the field fine
	 *
	 * @param fine The value to set
	 */
	public void setFine(Date fine) {
		this.fine = fine;
	}
}

