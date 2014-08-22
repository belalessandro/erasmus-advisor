package it.unipd.dei.bding.erasmusadvisor.resources;

import it.unipd.dei.bding.erasmusadvisor.beans.AreaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.CorsoDiLaureaBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nicola
 */

public class Course {
	
	private CorsoDiLaureaBean corso;
	private List<AreaBean> listaAree;
	
	/**
	 * @param corso
	 * @param aree
	 */
	public Course(CorsoDiLaureaBean corso,
			List<AreaBean> listaAree) {
		this.corso = corso;
		this.listaAree = listaAree;
	}

	/**
	 * Returns the field corso.
	 *
	 * @return the value of corso
	 */
	public CorsoDiLaureaBean getCorso() {
		return corso;
	}

	/**
	 * Sets the field corso
	 *
	 * @param corso The value to set
	 */
	public void setCorso(CorsoDiLaureaBean corso) {
		this.corso = corso;
	}

	/**
	 * Returns the field listaValutazioni.
	 *
	 * @return the value of listaValutazioni
	 */
	public List<AreaBean> getListaAree() {
		return listaAree;
	}

	/**
	 * Sets the field listaValutazioni
	 *
	 * @param listaValutazioni The value to set
	 */
	public void setListaAree(
			ArrayList<AreaBean> listaAree) {
		this.listaAree = listaAree;
	}
	
}
