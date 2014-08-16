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
class ValutazioneCittaBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -22416953176376311L;

	/**
	 *
	 */
	private String nomeUtenteStudente;

	/**
	 *
	 */
	private String nomeCitta;

	/**
	 *
	 */
	private String statoCitta;

	/**
	 *
	 */
	private int costoDellaVita;

	/**
	 *
	 */
	private int disponibilitaAlloggi;

	/**
	 *
	 */
	private int vivibilitaUrbana;

	/**
	 *
	 */
	private int vitaSociale;

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
	public ValutazioneCittaBean() {}

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
	 * Returns the field nomeCitta.
	 *
	 * @return the value of nomeCitta
	 */
	public String getNomeCitta() {
		return nomeCitta;
	}

	/**
	 * Sets the field nomeCitta
	 *
	 * @param nomeCitta The value to set
	 */
	public void setNomeCitta(String nomeCitta) {
		this.nomeCitta = nomeCitta;
	}

	/**
	 * Returns the field statoCitta.
	 *
	 * @return the value of statoCitta
	 */
	public String getStatoCitta() {
		return statoCitta;
	}

	/**
	 * Sets the field statoCitta
	 *
	 * @param statoCitta The value to set
	 */
	public void setStatoCitta(String statoCitta) {
		this.statoCitta = statoCitta;
	}

	/**
	 * Returns the field costoDellaVita.
	 *
	 * @return the value of costoDellaVita
	 */
	public int getCostoDellaVita() {
		return costoDellaVita;
	}

	/**
	 * Sets the field costoDellaVita
	 *
	 * @param costoDellaVita The value to set
	 */
	public void setCostoDellaVita(int costoDellaVita) {
		this.costoDellaVita = costoDellaVita;
	}

	/**
	 * Returns the field disponibilitaAlloggi.
	 *
	 * @return the value of disponibilitaAlloggi
	 */
	public int getDisponibilitaAlloggi() {
		return disponibilitaAlloggi;
	}

	/**
	 * Sets the field disponibilitaAlloggi
	 *
	 * @param disponibilitaAlloggi The value to set
	 */
	public void setDisponibilitaAlloggi(int disponibilitaAlloggi) {
		this.disponibilitaAlloggi = disponibilitaAlloggi;
	}

	/**
	 * Returns the field vivibilitaUrbana.
	 *
	 * @return the value of vivibilitaUrbana
	 */
	public int getVivibilitaUrbana() {
		return vivibilitaUrbana;
	}

	/**
	 * Sets the field vivibilitaUrbana
	 *
	 * @param vivibilitaUrbana The value to set
	 */
	public void setVivibilitaUrbana(int vivibilitaUrbana) {
		this.vivibilitaUrbana = vivibilitaUrbana;
	}

	/**
	 * Returns the field vitaSociale.
	 *
	 * @return the value of vitaSociale
	 */
	public int getVitaSociale() {
		return vitaSociale;
	}

	/**
	 * Sets the field vitaSociale
	 *
	 * @param vitaSociale The value to set
	 */
	public void setVitaSociale(int vitaSociale) {
		this.vitaSociale = vitaSociale;
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

