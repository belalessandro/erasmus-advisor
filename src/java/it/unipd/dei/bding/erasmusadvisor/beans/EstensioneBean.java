package it.unipd.dei.bding.erasmusadvisor.beans;

import java.io.Serializable;

/**
 * Represents the ...
 * 
 * @author Alessandro
 * @version 1.0
 *  
 */
class EstensioneBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1261818163793967769L;

	/**
	 *
	 */
	private int idArgomentoTesi;

	/**
	 *
	 */
	private String area;

	/**
	 * Empty constructor
	 */
	public EstensioneBean() {}

	/**
	 * Returns the field idArgomentoTesi.
	 *
	 * @return the value of idArgomentoTesi
	 */
	public int getIdArgomentoTesi() {
		return idArgomentoTesi;
	}

	/**
	 * Sets the field idArgomentoTesi
	 *
	 * @param idArgomentoTesi The value to set
	 */
	public void setIdArgomentoTesi(int idArgomentoTesi) {
		this.idArgomentoTesi = idArgomentoTesi;
	}

	/**
	 * Returns the field area.
	 *
	 * @return the value of area
	 */
	public String getArea() {
		return area;
	}

	/**
	 * Sets the field area
	 *
	 * @param area The value to set
	 */
	public void setArea(String area) {
		this.area = area;
	}
}

