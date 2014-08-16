package it.unipd.dei.bding.erasmusadvisor.beans;

import java.io.Serializable;

/**
 * Represents the ...
 * 
 * @author Alessandro
 * @version 1.0
 *  
 */
class ProfessoreBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3198627443771171259L;

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
	private String cognome;

	/**
	 * Empty constructor
	 */
	public ProfessoreBean() {}

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
	 * Returns the field cognome.
	 *
	 * @return the value of cognome
	 */
	public String getCognome() {
		return cognome;
	}

	/**
	 * Sets the field cognome
	 *
	 * @param cognome The value to set
	 */
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
}

