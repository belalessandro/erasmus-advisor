package it.unipd.dei.bding.erasmusadvisor.beans;

import java.io.Serializable;
import java.sql.Date;

/**
* Bean which represents the "ResponsabileFlusso" table in the database 
* 
* @author Alessandro
* @version 1.0
*  
*/
public class ResponsabileFlussoBean implements Serializable {
	
	private static final long serialVersionUID = 2625192943857821639L;

	/**
	 * Columns
	 */
	private String nomeUtente;
	private String nome;
	private String cognome;
	private String email;
	private Date dataRegistrazione;
	private String password;
	private String salt;
	private Date ultimoAccesso;
	private boolean attivo;
	private boolean abilitato;
	private String nomeUniversita;

	/**
	 * Empty constructor
	 */
	public ResponsabileFlussoBean() {}

	/**
	 * Returns the field nomeUtente.
	 *
	 * @return the value of nomeUtente
	 */
	public String getNomeUtente() {
		return nomeUtente;
	}

	/**
	 * Sets the field nomeUtente
	 *
	 * @param nomeUtente The value to set
	 */
	public void setNomeUtente(String nomeUtente) {
		this.nomeUtente = nomeUtente;
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

	/**
	 * Returns the field email.
	 *
	 * @return the value of email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the field email
	 *
	 * @param email The value to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Returns the field dataRegistrazione.
	 *
	 * @return the value of dataRegistrazione
	 */
	public Date getDataRegistrazione() {
		return dataRegistrazione;
	}

	/**
	 * Sets the field dataRegistrazione
	 *
	 * @param dataRegistrazione The value to set
	 */
	public void setDataRegistrazione(Date dataRegistrazione) {
		this.dataRegistrazione = dataRegistrazione;
	}

	/**
	 * Returns the field password.
	 *
	 * @return the value of password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the field password
	 *
	 * @param password The value to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Returns the field salt.
	 *
	 * @return the value of salt
	 */
	public String getSalt() {
		return salt;
	}

	/**
	 * Sets the field salt
	 *
	 * @param salt The value to set
	 */
	public void setSalt(String salt) {
		this.salt = salt;
	}

	/**
	 * Returns the field ultimoAccesso.
	 *
	 * @return the value of ultimoAccesso
	 */
	public Date getUltimoAccesso() {
		return ultimoAccesso;
	}

	/**
	 * Sets the field ultimoAccesso
	 *
	 * @param ultimoAccesso The value to set
	 */
	public void setUltimoAccesso(Date ultimoAccesso) {
		this.ultimoAccesso = ultimoAccesso;
	}

	/**
	 * Returns the field attivo.
	 *
	 * @return the value of attivo
	 */
	public boolean isAttivo() {
		return attivo;
	}

	/**
	 * Sets the field attivo
	 *
	 * @param attivo The value to set
	 */
	public void setAttivo(boolean attivo) {
		this.attivo = attivo;
	}
	
	/**
	 * Returns the field abilitato.
	 * 
	 * @return the value of attivo
	 */
	public boolean isAbilitato() {
		return abilitato;
	}
	
	/**
	 * Sets the field abilitato
	 * 
	 * @param abilitato the value to set
	 */
	public void setAbilitato(boolean abilitato) {
		this.abilitato = abilitato;
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
}

