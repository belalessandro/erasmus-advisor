package it.unipd.dei.bding.erasmusadvisor.resources;

import java.util.List;

import it.unipd.dei.bding.erasmusadvisor.beans.CertificatiLinguisticiBean;
import it.unipd.dei.bding.erasmusadvisor.beans.CorsoDiLaureaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.FlussoBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ResponsabileFlussoBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneFlussoBean;

/**
 * Contains all the details of a Flow displayed to an end user.
 * 
 * @author Luca
 *
 */
public class Flow 
{
	private FlussoBean flow;
	private ResponsabileFlussoBean responsabile;
	private List<CorsoDiLaureaBean> corsiOrigine;
	private List<CertificatiLinguisticiBean> certificati;
	private List<ValutazioneFlussoBean> listaValutazioni;
	
	/**
	 * Initializes the flow.
	 * 
	 * @param flow The flow.
	 * @param responsabile The flow manager.
	 * @param corsiOrigine The flow's starting degree courses.
	 * @param certificati The flow's required language certifications.
	 * @param listaValutazioni The flow's evaluations.
	 */
	public Flow(FlussoBean flow, ResponsabileFlussoBean responsabile, List<CorsoDiLaureaBean> corsiOrigine, 
			List<CertificatiLinguisticiBean> certificati, List<ValutazioneFlussoBean> listaValutazioni)
	{
		this.certificati = certificati;
		this.responsabile = responsabile;
		this.flow = flow;
		this.corsiOrigine = corsiOrigine;
		this.listaValutazioni = listaValutazioni;
	}
	
	/**
	 * Returns the flow manager.
	 * @return The flow manager.
	 */
	public ResponsabileFlussoBean getResponsabile()
	{
		return responsabile;
	}
	
	/**
	 * Sets The manager.
	 * @param obj The manager.
	 */
	public void setResponsabile(ResponsabileFlussoBean obj)
	{
		this.responsabile = obj;
	}
	
	/**
	 * Returns the required language certifications.
	 * @return A list of beans representing the certifications.
	 */
	public List<CertificatiLinguisticiBean> getCertificati()
	{
		return certificati;
	}
	
	/**
	 * Sets the required language certifications.
	 * @param obj A list of certificates.
	 */
	public void setCertificati(List<CertificatiLinguisticiBean> obj)
	{
		this.certificati = obj;
	}
	
	/**
	 * Returns the flow.
	 * @return The flow.
	 */
	public FlussoBean getFlusso()
	{
		return flow;
	}
	
	/**
	 * Sets the flow.
	 * @param obj The flow.
	 */
	public void setFlusso(FlussoBean obj)
	{
		this.flow = obj;
	}
	
	/**
	 * Returns the flow's starting degree courses.
	 * @return A list of degree courses.
	 */
	public List<CorsoDiLaureaBean> getCorsi()
	{
		return corsiOrigine;
	}
	
	/**
	 * Sets the flow starting degree courses.
	 * @param obj A list of degree courses.
	 */
	public void setCorsi(List<CorsoDiLaureaBean> obj)
	{
		this.corsiOrigine = obj;
	}
	
	/**
	 * Returns the flow's evaluations.
	 * @return A list of evaluations.
	 */
	public List<ValutazioneFlussoBean> getListaValutazioni()
	{
		return listaValutazioni;
	}
	
	/**
	 * Sets the flow evaluations.
	 * @param obj A list of evaluations.
	 */
	public void setListaValutazioni(List<ValutazioneFlussoBean> obj)
	{
		this.listaValutazioni = obj;
	} 
}
