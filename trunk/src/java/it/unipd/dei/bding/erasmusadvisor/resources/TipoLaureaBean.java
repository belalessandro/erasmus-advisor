package it.unipd.dei.bding.erasmusadvisor.resources;

import java.io.Serializable;

/**
 * Represents a degree type, such as undergraduate or graduate.
 * @author Luca
 *
 */
public class TipoLaureaBean implements Serializable
{
	
	private static final long serialVersionUID = 1896067718218383862L;
	private String tipoLaurea;

	/**
	 * Returns the degree type.
	 *
	 * @return The degree type.
	 */
	public String getTipoLaurea() {
		return tipoLaurea;
	}

	/**
	 * Sets the degree type.
	 *
	 * @param tipoLaurea The value to set.
	 */
	public void setTipoLaurea(String tipoLaurea) {
		this.tipoLaurea = tipoLaurea;
	}

	/**
	 * Empty constructor.
	 */
	public TipoLaureaBean() {}
}
