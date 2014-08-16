package it.unipd.dei.bding.erasmusadvisor.beans;

import java.io.Serializable;
import java.sql.Date;

/**
 * Represents the ...
 * 
 * @author Alessandro
 * @version 1.0
 *  
 */
class RiconoscimentoBean implements Serializable {
	/**
	 *
	 */
	private int idInsegnamento;

	/**
	 *
	 */
	private String idFlusso;

	/**
	 * Empty constructor
	 */
	public RiconoscimentoBean() {}

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
	 * Returns the field idFlusso.
	 *
	 * @return the value of idFlusso
	 */
	public String getIdFlusso() {
		return idFlusso;
	}

	/**
	 * Sets the field idFlusso
	 *
	 * @param idFlusso The value to set
	 */
	public void setIdFlusso(String idFlusso) {
		this.idFlusso = idFlusso;
	}
}

