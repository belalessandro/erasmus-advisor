package it.unipd.dei.bding.erasmusadvisor.resources;

import it.unipd.dei.bding.erasmusadvisor.beans.ArgomentoTesiBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneTesiBean;


import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Nicola
 *
 */

public class Thesis {
	
	
	/**
	 * @param arg
	 * @param listaValutazioni
	 */
	private ArgomentoTesiBean arg;
	private List<ValutazioneTesiBean> listaValutazioni;

	public Thesis(ArgomentoTesiBean arg,
			List<ValutazioneTesiBean> listaValutazioni) {
		this.arg = arg;
		this.listaValutazioni = listaValutazioni;
	}
	
	public Thesis(ArgomentoTesiBean arg) {
		this.arg = arg;
		listaValutazioni = null;
	}

	/**
	 * Returns the field arg.
	 *
	 * @return the value of argomentoTesi
	 */
	public ArgomentoTesiBean getArgomentoTesi() {
		return arg;
	}

	/**
	 * Sets the field arg
	 *
	 * @param universita The value to set
	 */
	public void setArgomentoTesi(ArgomentoTesiBean arg) {
		this.arg = arg;
	}

	/**
	 * Returns the field listaValutazioni.
	 *
	 * @return the value of listaValutazioni
	 */
	public List<ValutazioneTesiBean> getListaValutazioni() {
		return listaValutazioni;
	}

	/**
	 * Sets the field listaValutazioni
	 *
	 * @param listaValutazioni The value to set
	 */
	public void setListaValutazioni(
			ArrayList<ValutazioneTesiBean> listaValutazioni) {
		this.listaValutazioni = listaValutazioni;
	}
	/*public String toString()
	{
		String s = arg.getNome()+arg.getNomeUniversita()+arg.getStato()+arg.isTriennale()+arg.isMagistrale()+"";
		return s;
	}*/
}
