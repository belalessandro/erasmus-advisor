package it.unipd.dei.bding.erasmusadvisor.beans;

import java.io.Serializable;
import java.sql.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* Bean which represents the "Studente" table in the database 
* 
* @author Alessandro
* @version 1.0
*  
*/
public class StudenteBean implements Serializable {
	
	private static final long serialVersionUID = -8326089057059842805L;

	/**
	 * Columns
	 */
	private String nomeUtente;
	private String email;
	private Date dataRegistrazione;
	private String password;
	private String salt;
	private Date ultimoAccesso;
	private boolean attivo;

	/**
	 * Empty constructor
	 */
	public StudenteBean() {}

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
	 * Check for the field email
	 *  
	 * @return true if the email is valid
	 */
	public boolean isEmailValid() {
		String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
							 + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		
		return matcher.matches();
	}
    
}