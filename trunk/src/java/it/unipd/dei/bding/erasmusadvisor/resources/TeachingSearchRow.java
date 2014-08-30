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
	 * @param arg
	 * @param listaProfessori
	 */
	public TeachingSearchRow(InsegnamentoBean arg,
			List<ProfessoreBean> listaProfessori) {
		super();
		this.arg = arg;
		this.listaProfessori = listaProfessori;
	}
	/**
	 * @return the arg
	 */
	public InsegnamentoBean getArg() {
		return arg;
	}
	/**
	 * @param arg the arg to set
	 */
	public void setArg(InsegnamentoBean arg) {
		this.arg = arg;
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
	

}
	
