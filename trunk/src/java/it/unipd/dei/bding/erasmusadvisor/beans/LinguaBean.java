package it.unipd.dei.bding.erasmusadvisor.beans;

import java.io.Serializable;

/**
 * Represents the ...
 * 
 * @author Alessandro
 * @version 1.0
 *  
 */
class LinguaBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5540653167486979014L;

	/**
	 *
	 */
	private String sigla;

	/**
	 *
	 */
	private String nome;

	/**
	 * Empty constructor
	 */
	public LinguaBean() {}

	/**
	 * Returns the field sigla.
	 *
	 * @return the value of sigla
	 */
	public String getSigla() {
		return sigla;
	}

	/**
	 * Sets the field sigla
	 *
	 * @param sigla The value to set
	 */
	public void setSigla(String sigla) {
		this.sigla = sigla;
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
}

