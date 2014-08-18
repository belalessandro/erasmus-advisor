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
public class ValutazioneFlussoBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4999388511032220342L;

	/**
	 *
	 */
	private String nomeUtenteStudente;

	/**
	 *
	 */
	private String idFlusso;

	/**
	 *
	 */
	private int soddEsperienza;

	/**
	 *
	 */
	private int soddAccademica;

	/**
	 *
	 */
	private int didattica;

	/**
	 *
	 */
	private int valutazioneResponsabile;

	/**
	 *
	 */
	private Date dataInserimento;

	/**
	 *
	 */
	private String commento;

	/**
	 * Empty constructor
	 */
	public ValutazioneFlussoBean() {}

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
	 * Returns the field soddEsperienza.
	 *
	 * @return the value of soddEsperienza
	 */
	public int getSoddEsperienza() {
		return soddEsperienza;
	}

	/**
	 * Sets the field soddEsperienza
	 *
	 * @param soddEsperienza The value to set
	 */
	public void setSoddEsperienza(int soddEsperienza) {
		this.soddEsperienza = soddEsperienza;
	}

	/**
	 * Returns the field soddAccademica.
	 *
	 * @return the value of soddAccademica
	 */
	public int getSoddAccademica() {
		return soddAccademica;
	}

	/**
	 * Sets the field soddAccademica
	 *
	 * @param soddAccademica The value to set
	 */
	public void setSoddAccademica(int soddAccademica) {
		this.soddAccademica = soddAccademica;
	}

	/**
	 * Returns the field didattica.
	 *
	 * @return the value of didattica
	 */
	public int getDidattica() {
		return didattica;
	}

	/**
	 * Sets the field didattica
	 *
	 * @param didattica The value to set
	 */
	public void setDidattica(int didattica) {
		this.didattica = didattica;
	}

	/**
	 * Returns the field valutazioneResponsabile.
	 *
	 * @return the value of valutazioneResponsabile
	 */
	public int getValutazioneResponsabile() {
		return valutazioneResponsabile;
	}

	/**
	 * Sets the field valutazioneResponsabile
	 *
	 * @param valutazioneResponsabile The value to set
	 */
	public void setValutazioneResponsabile(int valutazioneResponsabile) {
		this.valutazioneResponsabile = valutazioneResponsabile;
	}

	/**
	 * Returns the field dataInserimento.
	 *
	 * @return the value of dataInserimento
	 */
	public Date getDataInserimento() {
		return dataInserimento;
	}

	/**
	 * Sets the field dataInserimento
	 *
	 * @param dataInserimento The value to set
	 */
	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	/**
	 * Returns the field commento.
	 *
	 * @return the value of commento
	 */
	public String getCommento() {
		return commento;
	}

	/**
	 * Sets the field commento
	 *
	 * @param commento The value to set
	 */
	public void setCommento(String commento) {
		this.commento = commento;
	}
}

