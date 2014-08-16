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
class InsegnamentoBean implements Serializable {
	/**
	 *
	 */
	private long id;

	/**
	 *
	 */
	private String nome;

	/**
	 *
	 */
	private int crediti;

	/**
	 *
	 */
	private String nomeUniversita;

	/**
	 *
	 */
	private int periodoErogazione;

	/**
	 *
	 */
	private String stato;

	/**
	 *
	 */
	private int annoCorso;

	/**
	 *
	 */
	private String nomeArea;

	/**
	 *
	 */
	private String nomeLingua;

	/**
	 * Empty constructor
	 */
	public InsegnamentoBean() {}

	/**
	 * Returns the field id.
	 *
	 * @return the value of id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets the field id
	 *
	 * @param id The value to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Returns the field nome.
	 *
	 * @return the value of nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Sets the field nome
	 *
	 * @param nome The value to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Returns the field crediti.
	 *
	 * @return the value of crediti
	 */
	public int getCrediti() {
		return crediti;
	}

	/**
	 * Sets the field crediti
	 *
	 * @param crediti The value to set
	 */
	public void setCrediti(int crediti) {
		this.crediti = crediti;
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
	 * Returns the field periodoErogazione.
	 *
	 * @return the value of periodoErogazione
	 */
	public int getPeriodoErogazione() {
		return periodoErogazione;
	}

	/**
	 * Sets the field periodoErogazione
	 *
	 * @param periodoErogazione The value to set
	 */
	public void setPeriodoErogazione(int periodoErogazione) {
		this.periodoErogazione = periodoErogazione;
	}

	/**
	 * Returns the field stato.
	 *
	 * @return the value of stato
	 */
	public String getStato() {
		return stato;
	}

	/**
	 * Sets the field stato
	 *
	 * @param stato The value to set
	 */
	public void setStato(String stato) {
		this.stato = stato;
	}

	/**
	 * Returns the field annoCorso.
	 *
	 * @return the value of annoCorso
	 */
	public int getAnnoCorso() {
		return annoCorso;
	}

	/**
	 * Sets the field annoCorso
	 *
	 * @param annoCorso The value to set
	 */
	public void setAnnoCorso(int annoCorso) {
		this.annoCorso = annoCorso;
	}

	/**
	 * Returns the field nomeArea.
	 *
	 * @return the value of nomeArea
	 */
	public String getNomeArea() {
		return nomeArea;
	}

	/**
	 * Sets the field nomeArea
	 *
	 * @param nomeArea The value to set
	 */
	public void setNomeArea(String nomeArea) {
		this.nomeArea = nomeArea;
	}

	/**
	 * Returns the field nomeLingua.
	 *
	 * @return the value of nomeLingua
	 */
	public String getNomeLingua() {
		return nomeLingua;
	}

	/**
	 * Sets the field nomeLingua
	 *
	 * @param nomeLingua The value to set
	 */
	public void setNomeLingua(String nomeLingua) {
		this.nomeLingua = nomeLingua;
	}
}

