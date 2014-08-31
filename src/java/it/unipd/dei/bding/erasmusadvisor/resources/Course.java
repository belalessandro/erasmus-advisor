package it.unipd.dei.bding.erasmusadvisor.resources;

import it.unipd.dei.bding.erasmusadvisor.beans.AreaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.CorsoDiLaureaBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains all the details of a Degree Course displayed to an end user.
 * @author Nicola
 */

public class Course {
	
	private CorsoDiLaureaBean corso;
	private List<AreaBean> listaAree;
	
	/**
	 * Initializes the course.
	 * @param corso The degree course.
	 * @param aree The course's areas.
	 */
	public Course(CorsoDiLaureaBean corso,
			List<AreaBean> listaAree) {
		this.corso = corso;
		this.listaAree = listaAree;
	}

	/**
	 * Returns the degree course.
	 *
	 * @return The degree course.
	 */
	public CorsoDiLaureaBean getCorso() {
		return corso;
	}

	/**
	 * Sets the degree course.
	 *
	 * @param corso The value to set.
	 */
	public void setCorso(CorsoDiLaureaBean corso) {
		this.corso = corso;
	}

	/**
	 * Returns the areas of the degree course.
	 *
	 * @return A list of areas.
	 */
	public List<AreaBean> getListaAree() {
		return listaAree;
	}

	/**
	 * Sets the areas of the degree course.
	 *
	 * @param listaAree The value to set.
	 */
	public void setListaAree(
			ArrayList<AreaBean> listaAree) {
		this.listaAree = listaAree;
	}
	
}
