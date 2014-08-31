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
	 * Initializes the object, setting the search results.
	 * @param citta The city.
	 * @param listaLingue A list of languages spoken in the city.
	 */
	public CitySearchRow(CittaBean citta, List<LinguaBean> listaLingue) {
		this.citta = citta;
		this.listaLingue = listaLingue;
	}

	/**
	 * Returns the city.
	 *
	 * @return The city.
	 */
	public CittaBean getCitta() {
		return citta;
	}

	/**
	 * Sets the city.
	 *
	 * @param citta The city.
	 */
	public void setCitta(CittaBean citta) {
		this.citta = citta;
	}

	/**
	 * Returns the list of languages spoken in the city.
	 *
	 * @return The list of languages spoken in the city.
	 */
	public List<LinguaBean> getListaLingue() {
		return listaLingue;
	}

	/**
	 * Sets the list of languages spoken in the city.
	 *
	 * @param listaLingue The list of languages spoken in the city.
	 */
	public void setListaLingue(List<LinguaBean> listaLingue) {
		this.listaLingue = listaLingue;
	}
	
	
}
