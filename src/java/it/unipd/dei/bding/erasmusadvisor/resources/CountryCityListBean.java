package it.unipd.dei.bding.erasmusadvisor.resources;

import it.unipd.dei.bding.erasmusadvisor.beans.CittaBean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Realizes a data structure that contains a list of all the cities located in a defined country.
 * @author Luca
 *
 */
public class CountryCityListBean {

	private List<String> cities;
	private String country;

	/**
	 * Returns the country name.
	 * @return The country name.
	 */
	public String getCountry()
	{
		return country;
	}
	
	/**
	 * Sets the country name.
	 * @param country The country name.
	 */
	public void setCountry(String country)
	{
		this.country = country;
	}
	
	/**
	 * Returns all the cities located in the same country.
	 * @return A list of names of cities.
	 */
	public List<String> getCities() {
		return cities;
	}

	/**
	 * Set the cities.
	 * @param cities A list of names of cities.
	 */
	public void setCities(List<String> cities) 
	{
		this.cities = cities;
	}

	/**
	 * Empty constructor.
	 */
	public CountryCityListBean() {}
	
	/**
	 * Creates a list of CountryCityListBean from a list of CittaBean, realizing a sort of map that links every country
	 * with the cities located there.
	 * @param in A list of cities, sorted by the field stato.
	 * @return A list of countries with their cities.
	 */
	public List<CountryCityListBean> initialize(List<CittaBean> in)
	{
		List<CountryCityListBean> list = new ArrayList<CountryCityListBean>();
		
		if (in == null || in.size() == 0)
			return list;

		String stato = "";
		int index = -1;
		
		for (Iterator<CittaBean> iter = in.iterator(); iter.hasNext(); ) 
		{
			CittaBean next = iter.next();
			if (!stato.equals(next.getStato()))
			{
				stato = next.getStato();
				index++;
				CountryCityListBean bean = new CountryCityListBean();
				bean.setCountry(next.getStato());
				ArrayList<String> tmp = new ArrayList<String>();
				tmp.add(next.getNome());
				bean.setCities(tmp);
				list.add(bean);
			}
			else
			{
				list.get(index).getCities().add(next.getNome());
			}
		}		
		return list;
	}
}
