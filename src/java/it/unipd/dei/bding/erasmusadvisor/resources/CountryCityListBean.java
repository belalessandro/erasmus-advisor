package it.unipd.dei.bding.erasmusadvisor.resources;

import it.unipd.dei.bding.erasmusadvisor.beans.CittaBean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CountryCityListBean {

	private List<String> cities;
	private String country;

	public String getCountry()
	{
		return country;
	}
	
	public void setCountry(String country)
	{
		this.country = country;
	}
	
	public List<String> getCities() {
		return cities;
	}

	public void setCities(List<String> cities) 
	{
		this.cities = cities;
	}

	/**
	 * Empty constructor
	 */
	public CountryCityListBean() {}
	
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
