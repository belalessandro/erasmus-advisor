package it.unipd.dei.bding.erasmusadvisor.beans;

import java.io.Serializable;
import java.sql.Date;

/**
* Bean which represents the "ValutazioneUniversita" table in the database 
* 
* @author Alessandro
* @version 1.0
*  
*/
public class ValutazioneUniversitaBean implements Serializable {
	
	private static final long serialVersionUID = 3337358926240396619L;

	/**
	 * Columns
	 */
	private String nomeUtenteStudente;
	private String nomeUniversita;
	private int collocazioneUrbana;
	private int iniziativeErasmus;
	private int qtaInsegnamenti;
	private int qtaAule;
	private Date dataInserimento;
	private String commento;

	/**
	 * Empty constructor
	 */
	public ValutazioneUniversitaBean() {}

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
	 * Returns the field nomeUniversita.
	 *
	 * @return the value of nomeUniversita
	 */
	public String getNomeUniversita() {
		return nomeUniversita;
	}

	/**
	 * Sets the field nomeUniversita
	 *
	 * @param nomeUniversita The value to set
	 */
	public void setNomeUniversita(String nomeUniversita) {
		this.nomeUniversita = nomeUniversita;
	}

	/**
	 * Returns the field collocazioneUrbana.
	 *
	 * @return the value of collocazioneUrbana
	 */
	public int getCollocazioneUrbana() {
		return collocazioneUrbana;
	}

	/**
	 * Sets the field collocazioneUrbana
	 *
	 * @param collocazioneUrbana The value to set
	 */
	public void setCollocazioneUrbana(int collocazioneUrbana) {
		this.collocazioneUrbana = collocazioneUrbana;
	}

	/**
	 * Returns the field iniziativeErasmus.
	 *
	 * @return the value of iniziativeErasmus
	 */
	public int getIniziativeErasmus() {
		return iniziativeErasmus;
	}

	/**
	 * Sets the field iniziativeErasmus
	 *
	 * @param iniziativeErasmus The value to set
	 */
	public void setIniziativeErasmus(int iniziativeErasmus) {
		this.iniziativeErasmus = iniziativeErasmus;
	}

	/**
	 * Returns the field qtaInsegnamenti.
	 *
	 * @return the value of qtaInsegnamenti
	 */
	public int getQtaInsegnamenti() {
		return qtaInsegnamenti;
	}

	/**
	 * Sets the field qtaInsegnamenti
	 *
	 * @param qtaInsegnamenti The value to set
	 */
	public void setQtaInsegnamenti(int qtaInsegnamenti) {
		this.qtaInsegnamenti = qtaInsegnamenti;
	}

	/**
	 * Returns the field qtaAule.
	 *
	 * @return the value of qtaAule
	 */
	public int getQtaAule() {
		return qtaAule;
	}

	/**
	 * Sets the field qtaAule
	 *
	 * @param qtaAule The value to set
	 */
	public void setQtaAule(int qtaAule) {
		this.qtaAule = qtaAule;
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
