package it.unipd.dei.bding.erasmusadvisor.beans;

import java.io.Serializable;

/**
 * Represents the ...
 * 
 * @author Alessandro
 * @version 1.0
 *  
 */
public class LinguaCittaBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 45028706066596831L;

	/**
	 *
	 */
	private String siglaLingua;

	/**
	 *
	 */
	private String nomeCitta;

	/**
	 *
	 */
	private String statoCitta;

	/**
	 * Empty constructor
	 */
	public LinguaCittaBean() {}

	/**
	 * Returns the field siglaLingua.
	 *
	 * @return the value of siglaLingua
	 */
	public String getSiglaLingua() {
		return siglaLingua;
	}

	/**
	 * Sets the field siglaLingua
	 *
	 * @param siglaLingua The value to set
	 */
	public void setSiglaLingua(String siglaLingua) {
		this.siglaLingua = siglaLingua;
	}

	/**
	 * Returns the field nomeCitta.
	 *
	 * @return the value of nomeCitta
	 */
	public String getNomeCitta() {
		return nomeCitta;
	}

	/**
	 * Sets the field nomeCitta
	 *
	 * @param nomeCitta The value to set
	 */
	public void setNomeCitta(String nomeCitta) {
		this.nomeCitta = nomeCitta;
	}

	/**
	 * Returns the field statoCitta.
	 *
	 * @return the value of statoCitta
	 */
	public String getStatoCitta() {
		return statoCitta;
	}

	/**
	 * Sets the field statoCitta
	 *
	 * @param statoCitta The value to set
	 */
	public void setStatoCitta(String statoCitta) {
		this.statoCitta = statoCitta;
	}
}

