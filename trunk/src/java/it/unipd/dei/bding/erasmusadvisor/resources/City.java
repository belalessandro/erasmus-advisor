package it.unipd.dei.bding.erasmusadvisor.resources;

import java.util.ArrayList;
import java.util.List;

import it.unipd.dei.bding.erasmusadvisor.beans.CittaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneCittaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.LinguaBean;

/**
 * Contains all the details of a City displayed to an end user.
 * 
 * @author Luca
 *
 */

public class City {
	
	private CittaBean citta;
	private List<ValutazioneCittaBean> listaValutazioni;
	private List<LinguaBean> listaLingue;
	
	/**
	 * Initializes the city
	 * @param city The city
	 * @param listaValutazioni A list of evaluations
	 * @param listaLingue A list of languages spoken in the city
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
	 * @return The city
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
	 * @return A list of evaluations
	 */
	public List<ValutazioneCittaBean> getEvalutationList() 
	{
		return listaValutazioni;
	}

	/**
	 * Sets the evaluations
	 *
	 * @param listaValutazioni A list of evaluations
	 */
	public void setEvalutationList(ArrayList<ValutazioneCittaBean> listaValutazioni) 
	{
		this.listaValutazioni = listaValutazioni;
	}
	
	/**
	 * Returns the languages spoken in the city.
	 *
	 * @return A list of languages
	 */
	public List<LinguaBean> getLanguagesList() 
	{
		return listaLingue;
	}

	/**
	 * Sets the languages spoken in the city.
	 *
	 * @param listaLingue A list of languages
	 */
	public void setLanguagesList(ArrayList<LinguaBean> listaLingue) 
	{
		this.listaLingue = listaLingue;
	}
	
}
