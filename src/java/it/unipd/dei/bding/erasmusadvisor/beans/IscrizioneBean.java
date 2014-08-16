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
class IscrizioneBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4462510397327053873L;

	/**
	 *
	 */
	private int idCorso;

	/**
	 *
	 */
	private String nomeUtenteStudente;

	/**
	 *
	 */
	private Date annoInizio;

	/**
	 *
	 */
	private Date annoFine;

	/**
	 * Empty constructor
	 */
	public IscrizioneBean() {}

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
	 * Returns the field annoInizio.
	 *
	 * @return the value of annoInizio
	 */
	public Date getAnnoInizio() {
		return annoInizio;
	}

	/**
	 * Sets the field annoInizio
	 *
	 * @param annoInizio The value to set
	 */
	public void setAnnoInizio(Date annoInizio) {
		this.annoInizio = annoInizio;
	}

	/**
	 * Returns the field annoFine.
	 *
	 * @return the value of annoFine
	 */
	public Date getAnnoFine() {
		return annoFine;
	}

	/**
	 * Sets the field annoFine
	 *
	 * @param annoFine The value to set
	 */
	public void setAnnoFine(Date annoFine) {
		this.annoFine = annoFine;
	}
}

