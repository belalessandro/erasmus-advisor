package it.unipd.dei.bding.erasmusadvisor.resources;

import it.unipd.dei.bding.erasmusadvisor.beans.InsegnamentoBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ProfessoreBean;
import java.util.List;

/**
 * Contains the results for the Teaching search JSP page.
 * 
 * @author Nicola
 *
 */
public class TeachingSearchRow {
	private InsegnamentoBean arg;
	private List<ProfessoreBean> listaProfessori;
	
	/**
	 * Initializes the object, setting the search results.
	 * @param arg The class.
	 * @param listaProfessori The list of class teachers.
	 */
	public TeachingSearchRow(InsegnamentoBean arg,
			List<ProfessoreBean> listaProfessori) {
		super();
		this.arg = arg;
		this.listaProfessori = listaProfessori;
	}
	
	/**
	 * Returns the class.
	 * @return The class.
	 */
	public InsegnamentoBean getArg() {
		return arg;
	}
	
	/**
	 * Sets the class.
	 * @param arg The class.
	 */
	public void setArg(InsegnamentoBean arg) {
		this.arg = arg;
	}
	
	/**
	 * Returns the list of class teachers.
	 * @return A list of teachers.
	 */
	public List<ProfessoreBean> getListaProfessori() {
		return listaProfessori;
	}
	
	/**
	 * Sets the class teachers.
	 * @param listaProfessori The value to set.
	 */
	public void setListaProfessori(List<ProfessoreBean> listaProfessori) {
		this.listaProfessori = listaProfessori;
	}
	
}
	
