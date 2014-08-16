package it.unipd.dei.bding.erasmusadvisor.beans;

import java.io.Serializable;

/**
 * Represents the ...
 * 
 * @author Alessandro
 * @version 1.0
 *  
 */
class UniversitaBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6881425108525773469L;

	/**
	 *
	 */
	private String nome;

	/**
	 *
	 */
	private String link;

	/**
	 *
	 */
	private int posizioneClassifica;

	/**
	 *
	 */
	private boolean presenzaAlloggi;

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
	public UniversitaBean() {}

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
	 * Returns the field link.
	 *
	 * @return the value of link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * Sets the field link
	 *
	 * @param link The value to set
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * Returns the field posizioneClassifica.
	 *
	 * @return the value of posizioneClassifica
	 */
	public int getPosizioneClassifica() {
		return posizioneClassifica;
	}

	/**
	 * Sets the field posizioneClassifica
	 *
	 * @param posizioneClassifica The value to set
	 */
	public void setPosizioneClassifica(int posizioneClassifica) {
		this.posizioneClassifica = posizioneClassifica;
	}

	/**
	 * Returns the field presenzaAlloggi.
	 *
	 * @return the value of presenzaAlloggi
	 */
	public boolean isPresenzaAlloggi() {
		return presenzaAlloggi;
	}

	/**
	 * Sets the field presenzaAlloggi
	 *
	 * @param presenzaAlloggi The value to set
	 */
	public void setPresenzaAlloggi(boolean presenzaAlloggi) {
		this.presenzaAlloggi = presenzaAlloggi;
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

