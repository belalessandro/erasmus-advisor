package it.unipd.dei.bding.erasmusadvisor.resources;

import java.util.ArrayList;
import java.util.List;

import it.unipd.dei.bding.erasmusadvisor.beans.UniversitaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneUniversitaBean;

public class University {
	
	private UniversitaBean universita;
	
	/**
	 * @param universita
	 * @param listaValutazioni
	 */
	public University(UniversitaBean universita,
			List<ValutazioneUniversitaBean> listaValutazioni) {
		this.universita = universita;
		this.listaValutazioni = listaValutazioni;
	}

	private List<ValutazioneUniversitaBean> listaValutazioni;

	/**
	 * Returns the field universita.
	 *
	 * @return the value of universita
	 */
	public UniversitaBean getUniversita() {
		return universita;
	}

	/**
	 * Sets the field universita
	 *
	 * @param universita The value to set
	 */
	public void setUniversita(UniversitaBean universita) {
		this.universita = universita;
	}

	/**
	 * Returns the field listaValutazioni.
	 *
	 * @return the value of listaValutazioni
	 */
	public List<ValutazioneUniversitaBean> getListaValutazioni() {
		return listaValutazioni;
	}

	/**
	 * Sets the field listaValutazioni
	 *
	 * @param listaValutazioni The value to set
	 */
	public void setListaValutazioni(
			ArrayList<ValutazioneUniversitaBean> listaValutazioni) {
		this.listaValutazioni = listaValutazioni;
	}
	
}
