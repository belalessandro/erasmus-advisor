package it.unipd.dei.bding.erasmusadvisor.beans;

import java.io.Serializable;

/**
 * Represents the ...
 * 
 * @author Alessandro
 * @version 1.0
 *  
 */
class ArgomentoTesiBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2260439098317947249L;

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
	private String nomeUniversita;

	/**
	 *
	 */
	private boolean triennale;

	/**
	 *
	 */
	private boolean magistrale;

	/**
	 *
	 */
	private String stato;

	/**
	 * Empty constructor
	 */
	public ArgomentoTesiBean() {}

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
	 * Returns the field triennale.
	 *
	 * @return the value of triennale
	 */
	public boolean isTriennale() {
		return triennale;
	}

	/**
	 * Sets the field triennale
	 *
	 * @param triennale The value to set
	 */
	public void setTriennale(boolean triennale) {
		this.triennale = triennale;
	}

	/**
	 * Returns the field magistrale.
	 *
	 * @return the value of magistrale
	 */
	public boolean isMagistrale() {
		return magistrale;
	}

	/**
	 * Sets the field magistrale
	 *
	 * @param magistrale The value to set
	 */
	public void setMagistrale(boolean magistrale) {
		this.magistrale = magistrale;
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
}

