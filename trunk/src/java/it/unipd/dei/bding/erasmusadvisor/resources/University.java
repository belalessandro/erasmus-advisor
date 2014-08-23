package it.unipd.dei.bding.erasmusadvisor.resources;

import it.unipd.dei.bding.erasmusadvisor.beans.UniversitaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneUniversitaBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains all the details of a University needed by an end user.
 * 
 * @author Luca, Alessandro
 *
 */

public class University {
	
	private UniversitaBean universita;
	private List<ValutazioneUniversitaBean> listaValutazioni;
	
	/**
	 * Initialize the class.
	 * 
	 * @param universita the University
	 * @param listaValutazioni the list of the University evalutaions
	 */
	public University(UniversitaBean universita,
			List<ValutazioneUniversitaBean> listaValutazioni) {
		this.universita = universita;
		this.listaValutazioni = listaValutazioni;
	}

	/**
	 * Returns the University.
	 *
	 * @return a bean representing the University
	 */
	public UniversitaBean getUniversita() {
		return universita;
	}

	/**
	 * Sets the University
	 *
	 * @param universita The value to set
	 */
	public void setUniversita(UniversitaBean universita) {
		this.universita = universita;
	}

	/**
	 * Returns the evaluations to the University
	 *
	 * @return a list of beans representing the evaluations
	 */
	public List<ValutazioneUniversitaBean> getListaValutazioni() {
		return listaValutazioni;
	}

	/**
	 * Sets the evaluations to the University
	 *
	 * @param listaValutazioni the list of evaluations
	 */
	public void setListaValutazioni(
			ArrayList<ValutazioneUniversitaBean> listaValutazioni) {
		this.listaValutazioni = listaValutazioni;
	}
	
}
