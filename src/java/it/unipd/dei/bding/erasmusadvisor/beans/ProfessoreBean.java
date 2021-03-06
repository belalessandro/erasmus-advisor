package it.unipd.dei.bding.erasmusadvisor.beans;

import java.io.Serializable;

/**
* Bean which represents the "Professore" table in the database 
* 
* @author Alessandro
* @version 1.0
*  
*/
public class ProfessoreBean implements Serializable {
	
	private static final long serialVersionUID = 3198627443771171259L;

	/**
	 * Columns
	 */
	private int id;
	private String nome;
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
	public int getId() {
		return id;
	}

	/**
	 * Sets the field id
	 *
	 * @param id The value to set
	 */
	public void setId(int id) {
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

