package it.unipd.dei.bding.erasmusadvisor.resources;

import java.io.Serializable;

public class TipoLaureaBean implements Serializable
{
	
	/**
	 *
	 */
	private String tipoLaurea;

	/**
	 * Returns the field nome.
	 *
	 * @return the value of nome
	 */
	public String getTipoLaurea() {
		return tipoLaurea;
	}

	/**
	 * Sets the field nome
	 *
	 * @param nome The value to set
	 */
	public void setTipoLaurea(String tipoLaurea) {
		this.tipoLaurea = tipoLaurea;
	}

	/**
	 * Empty constructor
	 */
	public TipoLaureaBean() {}
}
