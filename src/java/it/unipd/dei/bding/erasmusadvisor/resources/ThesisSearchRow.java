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
	 * @param arg
	 * @param listaAree
	 * @param listaProfessori
	 * @param listaLingue
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
	 * @return the arg
	 */
	public ArgomentoTesiBean getArg() {
		return arg;
	}
	/**
	 * @param arg the arg to set
	 */
	public void setArg(ArgomentoTesiBean arg) {
		this.arg = arg;
	}
	/**
	 * @return the listaAree
	 */
	public List<AreaBean> getListaAree() {
		return listaAree;
	}
	/**
	 * @param listaAree the listaAree to set
	 */
	public void setListaAree(List<AreaBean> listaAree) {
		this.listaAree = listaAree;
	}
	/**
	 * @return the listaProfessori
	 */
	public List<ProfessoreBean> getListaProfessori() {
		return listaProfessori;
	}
	/**
	 * @param listaProfessori the listaProfessori to set
	 */
	public void setListaProfessori(List<ProfessoreBean> listaProfessori) {
		this.listaProfessori = listaProfessori;
	}
	/**
	 * @return the listaLingue
	 */
	public List<LinguaBean> getListaLingue() {
		return listaLingue;
	}
	/**
	 * @param listaLingue the listaLingue to set
	 */
	public void setListaLingue(List<LinguaBean> listaLingue) {
		this.listaLingue = listaLingue;
	}
}
	
