package it.unipd.dei.bding.erasmusadvisor.beans;

import java.io.Serializable;

/**
 * Represents the ...
 * 
 * @author Alessandro
 * @version 1.0
 *  
 */
public class AreaBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 313939512860366547L;
	
	/**
	 *
	 */
	private String nome;

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
	 * Empty constructor
	 */
	public AreaBean() {}
}

