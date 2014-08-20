package it.unipd.dei.bding.erasmusadvisor.resources;

import java.util.ArrayList;
import java.util.List;

import it.unipd.dei.bding.erasmusadvisor.beans.CittaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneCittaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.LinguaBean;


public class City {
	
	private CittaBean citta;
	private List<ValutazioneCittaBean> listaValutazioni;
	private List<LinguaBean> listaLingue;
	
	/**
	 * @param city
	 * @param listaValutazioni
	 */
	public City(CittaBean city, List<ValutazioneCittaBean> listaValutazioni, List<LinguaBean> listaLingue) 
	{
		this.citta = city;
		this.listaValutazioni = listaValutazioni;
		this.listaLingue = listaLingue;
	}


	/**
	 * Returns the field city.
	 *
	 * @return the value of city
	 */
	public CittaBean getCity() 
	{
		return citta;
	}

	/**
	 * Sets the field city
	 *
	 * @param universita The value to set
	 */
	public void setCity(CittaBean city) 
	{
		this.citta = city;
	}

	/**
	 * Returns the field listaValutazioni.
	 *
	 * @return the value of listaValutazioni
	 */
	public List<ValutazioneCittaBean> getEvalutationList() 
	{
		return listaValutazioni;
	}

	/**
	 * Sets the field listaValutazioni
	 *
	 * @param listaValutazioni The value to set
	 */
	public void setEvalutationList(ArrayList<ValutazioneCittaBean> listaValutazioni) 
	{
		this.listaValutazioni = listaValutazioni;
	}
	/**
	 * Returns the field listaLingue.
	 *
	 * @return the value of listaLingue
	 */
	public List<LinguaBean> getLanguagesList() 
	{
		return listaLingue;
	}

	/**
	 * Sets the field listaLingue
	 *
	 * @param listaLingue The value to set
	 */
	public void setLanguagesList(ArrayList<LinguaBean> listaLingue) 
	{
		this.listaLingue = listaLingue;
	}
	
}
