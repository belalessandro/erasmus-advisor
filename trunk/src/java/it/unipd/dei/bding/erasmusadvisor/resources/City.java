package it.unipd.dei.bding.erasmusadvisor.resources;

import java.util.ArrayList;
import java.util.List;

import it.unipd.dei.bding.erasmusadvisor.beans.CittaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneCittaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.LinguaBean;

/**
 * Contains all the details of a City needed by an end user.
 * 
 * @author Luca
 *
 */

public class City {
	
	private CittaBean citta;
	private List<ValutazioneCittaBean> listaValutazioni;
	private List<LinguaBean> listaLingue;
	
	/**
	 * Initialize the city
	 * @param city the city
	 * @param listaValutazioni a list of evaluations
	 * @param listaLingue a list of languages spoken in the city
	 */
	public City(CittaBean city, List<ValutazioneCittaBean> listaValutazioni, List<LinguaBean> listaLingue) 
	{
		this.citta = city;
		this.listaValutazioni = listaValutazioni;
		this.listaLingue = listaLingue;
	}


	/**
	 * Returns the city.
	 *
	 * @return the city
	 */
	public CittaBean getCity() 
	{
		return citta;
	}

	/**
	 * Sets the city
	 *
	 * @param city The city
	 */
	public void setCity(CittaBean city) 
	{
		this.citta = city;
	}

	/**
	 * Returns the evaluations.
	 *
	 * @return a list of evaluations
	 */
	public List<ValutazioneCittaBean> getEvalutationList() 
	{
		return listaValutazioni;
	}

	/**
	 * Sets the evaluations
	 *
	 * @param listaValutazioni a list of evaluations
	 */
	public void setEvalutationList(ArrayList<ValutazioneCittaBean> listaValutazioni) 
	{
		this.listaValutazioni = listaValutazioni;
	}
	
	/**
	 * Returns the languages spoken in the city.
	 *
	 * @return a list of languages
	 */
	public List<LinguaBean> getLanguagesList() 
	{
		return listaLingue;
	}

	/**
	 * Sets the languages spoken in the city.
	 *
	 * @param listaLingue a list of languages
	 */
	public void setLanguagesList(ArrayList<LinguaBean> listaLingue) 
	{
		this.listaLingue = listaLingue;
	}
	
}
