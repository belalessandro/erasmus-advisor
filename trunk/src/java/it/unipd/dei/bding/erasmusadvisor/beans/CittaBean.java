package it.unipd.dei.bding.erasmusadvisor.beans;

import java.io.Serializable;

/**
 * Represents the ...
 * 
 * @author Alessandro
 * @version 1.0
 *  
 */
public class CittaBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5963735648471148344L;

	/**
	 *
	 */
	private String nome;

	/**
	 *
	 */
	private String stato;

	/**
	 * Empty constructor
	 */
	public CittaBean() {}

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

