package it.unipd.dei.bding.erasmusadvisor.beans;

import java.io.Serializable;

/**
 * Represents the ...
 * 
 * @author Alessandro
 * @version 1.0
 *  
 */
public class SvolgimentoBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4929758988504272004L;

	/**
	 *
	 */
	private int idInsegnamento;

	/**
	 *
	 */
	private int idProfessore;

	/**
	 * Empty constructor
	 */
	public SvolgimentoBean() {}

	/**
	 * Returns the field idInsegnamento.
	 *
	 * @return the value of idInsegnamento
	 */
	public int getIdInsegnamento() {
		return idInsegnamento;
	}

	/**
	 * Sets the field idInsegnamento
	 *
	 * @param idInsegnamento The value to set
	 */
	public void setIdInsegnamento(int idInsegnamento) {
		this.idInsegnamento = idInsegnamento;
	}

	/**
	 * Returns the field idProfessore.
	 *
	 * @return the value of idProfessore
	 */
	public int getIdProfessore() {
		return idProfessore;
	}

	/**
	 * Sets the field idProfessore
	 *
	 * @param idProfessore The value to set
	 */
	public void setIdProfessore(int idProfessore) {
		this.idProfessore = idProfessore;
	}
}

