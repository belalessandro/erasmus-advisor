package it.unipd.dei.bding.erasmusadvisor.beans;

import java.io.Serializable;

/**
 * Represents the ...
 * 
 * @author Alessandro
 * @version 1.0
 *  
 */
public class CorsoDiLaureaBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1430601017206451611L;
	/**
	 * Constants for enum TIPOLAUREA
	 */
	private static final String UNDERGRADUATE = "UNDERGRADUATE";
	private static final String GRADUATE = "GRADUATE";
	private static final String UNIQUE = "UNIQUE";
	
	/**
	 *
	 */
	private int id;

	/**
	 *
	 */
	private String nome;

	/**
	 *
	 */
	private String livello;

	/**
	 *
	 */
	private String nomeUniversita;

	/**
	 * Empty constructor
	 */
	public CorsoDiLaureaBean() {}

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
	 * Returns the field livello.
	 *
	 * @return the value of livello
	 */
	public String getLivello() {
		return livello;
	}

	/**
	 * Sets the field livello
	 *
	 * @param livello The value to set
	 */
	public void setLivello(String livello) {
		this.livello = livello;
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
	 * Returns the field undergraduate.
	 *
	 * @return the value of undergraduate
	 */
	public static String getUndergraduate() {
		return UNDERGRADUATE;
	}

	/**
	 * Returns the field graduate.
	 *
	 * @return the value of graduate
	 */
	public static String getGraduate() {
		return GRADUATE;
	}

	/**
	 * Returns the field unique.
	 *
	 * @return the value of unique
	 */
	public static String getUnique() {
		return UNIQUE;
	}
}

