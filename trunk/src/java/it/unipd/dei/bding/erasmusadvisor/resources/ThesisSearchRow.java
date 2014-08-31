package it.unipd.dei.bding.erasmusadvisor.resources;

import it.unipd.dei.bding.erasmusadvisor.beans.AreaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ArgomentoTesiBean;
import it.unipd.dei.bding.erasmusadvisor.beans.LinguaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ProfessoreBean;

import java.util.List;

/**
 * Contains the results for the Theses search JSP page.
 * 
 * @author Nicola
 *
 */
public class ThesisSearchRow {
	private ArgomentoTesiBean arg;
	private List<AreaBean> listaAree;
	private List<ProfessoreBean> listaProfessori;
	private List<LinguaBean> listaLingue;
	
	
	/**
	 * Initializes the object, setting the search results.
	 * @param arg The thesis.
	 * @param listaAree The thesis' areas.
	 * @param listaProfessori The thesis' teachers.
	 * @param listaLingue The thesis' languages.
	 */
	public ThesisSearchRow(ArgomentoTesiBean arg, List<AreaBean> listaAree,
			List<ProfessoreBean> listaProfessori, List<LinguaBean> listaLingue) {
		super();
		this.arg = arg;
		this.listaAree = listaAree;
		this.listaProfessori = listaProfessori;
		this.listaLingue = listaLingue;
	}
	
	/**
	 * Returns the thesis.
	 * @return The thesis.
	 */
	public ArgomentoTesiBean getArg() {
		return arg;
	}
	
	/**
	 * Sets the thesis.
	 * @param arg The value to set.
	 */
	public void setArg(ArgomentoTesiBean arg) {
		this.arg = arg;
	}
	
	/**
	 * Returns the thesis areas.
	 * @return A list of areas.
	 */
	public List<AreaBean> getListaAree() {
		return listaAree;
	}
	
	/**
	 * Sets the thesis' areas.
	 * @param listaAree The value to set.
	 */
	public void setListaAree(List<AreaBean> listaAree) {
		this.listaAree = listaAree;
	}
	
	/**
	 * Returns the thesis teachers.
	 * @return A list of teachers.
	 */
	public List<ProfessoreBean> getListaProfessori() {
		return listaProfessori;
	}
	
	/**
	 * Sets the thesis teachers.
	 * @param listaProfessori The value to set.
	 */
	public void setListaProfessori(List<ProfessoreBean> listaProfessori) {
		this.listaProfessori = listaProfessori;
	}
	
	/**
	 * Returns the thesis' languages.
	 * @return A list of languages.
	 */
	public List<LinguaBean> getListaLingue() {
		return listaLingue;
	}
	
	/**
	 * Set the thesis' languages.
	 * @param listaLingue The value to set.
	 */
	public void setListaLingue(List<LinguaBean> listaLingue) {
		this.listaLingue = listaLingue;
	}
}
	
