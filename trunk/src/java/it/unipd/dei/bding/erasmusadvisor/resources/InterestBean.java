package it.unipd.dei.bding.erasmusadvisor.resources;

import java.io.Serializable;

/**
 * Represents an interest expressed by a student towards a flow, with all its informations.
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
	
	/**
	 * Empty constructor.
	 */
	public InterestBean() {}
	
	/**
	 * Returns the ID of the flows that the student is interested in.
	 * @return The flow ID.
	 */
	public String getFlowID()
	{
		return flowID;
	}
	
	/**
	 * Sets the ID of the flows that the student is interested in.
	 * @param bean The flow ID.
	 */
	public void setFlowID(String bean)
	{
		this.flowID = bean;
	}
	
	/** 
	 * Returns the name of the interested flow's destination university.
	 * @return The name of the university.
	 */
	public String getUniversityName()
	{
		return universityName;
	}
	
	/**
	 * Sets the name of the interested flow's destination university.
	 * @param bean The name of the university.
	 */
	public void setUniversityName(String bean)
	{
		this.universityName = bean;
	}
	
	/**
	 * Returns the name of the city in which the destination university is located.
	 * @return The city name.
	 */
	public String getCityName()
	{
		return cityName;
	}
	
	/**
	 * Sets the name of the city in which the destination university is located.
	 * @param bean The city name.
	 */
	public void setCityName(String bean)
	{
		this.cityName = bean;
	}
	
	/**
	 * Returns the name of the country in which the destination university is located.
	 * @return The country name.
	 */
	public String getCountryName()
	{
		return countryName;
	}
	
	/**
	 * Sets the name of the country in which the destination university is located.
	 * @param bean The country name.
	 */
	public void setCountryName(String bean)
	{
		this.countryName = bean;
	}
	
	/**
	 * Returns the user name of the student that has expressed the interest.
	 * @return The user name.
	 */
	public String getUserName()
	{
		return userName;
	}
	
	/**
	 * Sets the user name that has expressed the interest.
	 * @param bean The user name.
	 */
	public void setUserName(String bean)
	{
		this.userName = bean;
	}
}
