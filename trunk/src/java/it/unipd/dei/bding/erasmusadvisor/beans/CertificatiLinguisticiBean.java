package it.unipd.dei.bding.erasmusadvisor.beans;

import java.io.Serializable;

/**
 * Represents the ...
 * 
 * @author Alessandro
 * @version 1.0
 *  
 */
public class CertificatiLinguisticiBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5222780917535416426L;

	/**
	 *
	 */
	private String nomeLingua;

	/**
	 *
	 */
	private String livello;

	/**
	 * Empty constructor
	 */
	public CertificatiLinguisticiBean() {}

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
}

