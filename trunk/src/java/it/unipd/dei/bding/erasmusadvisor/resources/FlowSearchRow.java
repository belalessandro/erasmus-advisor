package it.unipd.dei.bding.erasmusadvisor.resources;

import it.unipd.dei.bding.erasmusadvisor.beans.CertificatiLinguisticiBean;
import it.unipd.dei.bding.erasmusadvisor.beans.FlussoBean;
import it.unipd.dei.bding.erasmusadvisor.beans.UniversitaBean;

import java.util.List;

/**
 * Contains the results for the Flow search JSP page.
 * 
 * @author Alessandro
 *
 */
public class FlowSearchRow {
	private FlussoBean flusso;
	private UniversitaBean universita;
	private List<CertificatiLinguisticiBean> listaCertificatiLinguistici;
	
	/**
	 * Initializes the object, setting the search results.
	 * @param flusso The flow.
	 * @param universita The university.
	 * @param listaCertificatiLinguistici The list of language certifications.
	 */
	public FlowSearchRow(FlussoBean flusso, UniversitaBean universita,
			List<CertificatiLinguisticiBean> listaCertificatiLinguistici) {
		this.flusso = flusso;
		this.universita = universita;
		this.listaCertificatiLinguistici = listaCertificatiLinguistici;
	}

	/**
	 * Returns the flow.
	 *
	 * @return The flow.
	 */
	public FlussoBean getFlusso() {
		return flusso;
	}

	/**
	 * Sets the flow.
	 *
	 * @param flusso The value to set.
	 */
	public void setFlusso(FlussoBean flusso) {
		this.flusso = flusso;
	}

	/**
	 * Returns the university.
	 *
	 * @return The university.
	 */
	public UniversitaBean getUniversita() {
		return universita;
	}

	/**
	 * Sets the university.
	 *
	 * @param universita The value to set.
	 */
	public void setUniversita(UniversitaBean universita) {
		this.universita = universita;
	}

	/**
	 * Returns the list of language certifications.
	 *
	 * @return A list of language certifications.
	 */
	public List<CertificatiLinguisticiBean> getListaCertificatiLinguistici() {
		return listaCertificatiLinguistici;
	}

	/**
	 * Sets list of language certifications.
	 *
	 * @param listaCertificatiLinguistici The value to set.
	 */
	public void setListaCertificatiLinguistici(
			List<CertificatiLinguisticiBean> listaCertificatiLinguistici) {
		this.listaCertificatiLinguistici = listaCertificatiLinguistici;
	}
	
	
	
}
