package it.unipd.dei.bding.erasmusadvisor.resources;

import it.unipd.dei.bding.erasmusadvisor.beans.CertificatiLinguisticiBean;
import it.unipd.dei.bding.erasmusadvisor.beans.CittaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.FlussoBean;
import it.unipd.dei.bding.erasmusadvisor.beans.LinguaBean;
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
	 * @param flusso
	 * @param universita
	 * @param listaCertificatiLinguistici
	 */
	public FlowSearchRow(FlussoBean flusso, UniversitaBean universita,
			List<CertificatiLinguisticiBean> listaCertificatiLinguistici) {
		this.flusso = flusso;
		this.universita = universita;
		this.listaCertificatiLinguistici = listaCertificatiLinguistici;
	}

	/**
	 * Returns the field flusso.
	 *
	 * @return the value of flusso
	 */
	public FlussoBean getFlusso() {
		return flusso;
	}

	/**
	 * Sets the field flusso
	 *
	 * @param flusso The value to set
	 */
	public void setFlusso(FlussoBean flusso) {
		this.flusso = flusso;
	}

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
	 * Returns the field listaCertificatiLinguistici.
	 *
	 * @return the value of listaCertificatiLinguistici
	 */
	public List<CertificatiLinguisticiBean> getListaCertificatiLinguistici() {
		return listaCertificatiLinguistici;
	}

	/**
	 * Sets the field listaCertificatiLinguistici
	 *
	 * @param listaCertificatiLinguistici The value to set
	 */
	public void setListaCertificatiLinguistici(
			List<CertificatiLinguisticiBean> listaCertificatiLinguistici) {
		this.listaCertificatiLinguistici = listaCertificatiLinguistici;
	}
	
	
	
}
