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
class ValutazioneInsegnamentoBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4780429788958187651L;

	/**
	 *
	 */
	private String nomeUtenteStudente;

	/**
	 *
	 */
	private int idInsegnamento;

	/**
	 *
	 */
	private int qtaInsegnamanto;

	/**
	 *
	 */
	private int interesse;

	/**
	 *
	 */
	private int difficolta;

	/**
	 *
	 */
	private int rispettoDelleOre;

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
	public ValutazioneInsegnamentoBean() {}

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
	 * Returns the field idInsegnamento.
	 *
	 * @return the value of idInsegnamento
	 */
	public int getIdInsegnamento() {
		return idInsegnamento;
	}

	/**
	 * Sets the field idInsegnamento
	 *
	 * @param idInsegnamento The value to set
	 */
	public void setIdInsegnamento(int idInsegnamento) {
		this.idInsegnamento = idInsegnamento;
	}

	/**
	 * Returns the field qtaInsegnamanto.
	 *
	 * @return the value of qtaInsegnamanto
	 */
	public int getQtaInsegnamanto() {
		return qtaInsegnamanto;
	}

	/**
	 * Sets the field qtaInsegnamanto
	 *
	 * @param qtaInsegnamanto The value to set
	 */
	public void setQtaInsegnamanto(int qtaInsegnamanto) {
		this.qtaInsegnamanto = qtaInsegnamanto;
	}

	/**
	 * Returns the field interesse.
	 *
	 * @return the value of interesse
	 */
	public int getInteresse() {
		return interesse;
	}

	/**
	 * Sets the field interesse
	 *
	 * @param interesse The value to set
	 */
	public void setInteresse(int interesse) {
		this.interesse = interesse;
	}

	/**
	 * Returns the field difficolta.
	 *
	 * @return the value of difficolta
	 */
	public int getDifficolta() {
		return difficolta;
	}

	/**
	 * Sets the field difficolta
	 *
	 * @param difficolta The value to set
	 */
	public void setDifficolta(int difficolta) {
		this.difficolta = difficolta;
	}

	/**
	 * Returns the field rispettoDelleOre.
	 *
	 * @return the value of rispettoDelleOre
	 */
	public int getRispettoDelleOre() {
		return rispettoDelleOre;
	}

	/**
	 * Sets the field rispettoDelleOre
	 *
	 * @param rispettoDelleOre The value to set
	 */
	public void setRispettoDelleOre(int rispettoDelleOre) {
		this.rispettoDelleOre = rispettoDelleOre;
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

