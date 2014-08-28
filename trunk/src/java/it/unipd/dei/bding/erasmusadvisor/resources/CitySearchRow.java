package it.unipd.dei.bding.erasmusadvisor.resources;

import it.unipd.dei.bding.erasmusadvisor.beans.CittaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.LinguaBean;

import java.util.List;

/**
 * Contains the results for the City search JSP page.
 * 
 * @author Alessandro
 *
 */
public class CitySearchRow {
	private CittaBean citta;
	private List<LinguaBean> listaLingue;
	
	/**
	 * @param citta
	 * @param listaLingue
	 */
	public CitySearchRow(CittaBean citta, List<LinguaBean> listaLingue) {
		this.citta = citta;
		this.listaLingue = listaLingue;
	}

	/**
	 * Returns the field citta.
	 *
	 * @return the value of citta
	 */
	public CittaBean getCitta() {
		return citta;
	}

	/**
	 * Sets the field citta
	 *
	 * @param citta The value to set
	 */
	public void setCitta(CittaBean citta) {
		this.citta = citta;
	}

	/**
	 * Returns the field listaLingue.
	 *
	 * @return the value of listaLingue
	 */
	public List<LinguaBean> getListaLingue() {
		return listaLingue;
	}

	/**
	 * Sets the field listaLingue
	 *
	 * @param listaLingue The value to set
	 */
	public void setListaLingue(List<LinguaBean> listaLingue) {
		this.listaLingue = listaLingue;
	}
	
	
}
