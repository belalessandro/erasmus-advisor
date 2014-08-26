package it.unipd.dei.bding.erasmusadvisor.resources;

import java.io.Serializable;

/**
 * Represent an Interest with all its informations
 * @author Luca
 *
 */
public class InterestBean implements Serializable
{
	private static final long serialVersionUID = 1275882227201955725L;
	
	private String flowID;
	private String universityName;
	private String cityName;
	private String countryName;
	private String userName;
	
	public InterestBean() {}
	
	public String getFlowID()
	{
		return flowID;
	}
	
	public void setFlowID(String bean)
	{
		this.flowID = bean;
	}
	
	public String getUniversityName()
	{
		return universityName;
	}
	
	public void setUniversityName(String bean)
	{
		this.universityName = bean;
	}
	
	public String getCityName()
	{
		return cityName;
	}
	
	public void setCityName(String bean)
	{
		this.cityName = bean;
	}
	
	public String getCountryName()
	{
		return countryName;
	}
	
	public void setCountryName(String bean)
	{
		this.countryName = bean;
	}
	
	public String getUserName()
	{
		return userName;
	}
	
	public void setUserName(String bean)
	{
		this.userName = bean;
	}
}
