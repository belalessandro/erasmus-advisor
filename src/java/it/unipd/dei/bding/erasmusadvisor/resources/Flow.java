package it.unipd.dei.bding.erasmusadvisor.resources;

import java.util.List;

import it.unipd.dei.bding.erasmusadvisor.beans.CertificatiLinguisticiBean;
import it.unipd.dei.bding.erasmusadvisor.beans.CorsoDiLaureaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.FlussoBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ResponsabileFlussoBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneFlussoBean;

/**
 * Contains all the details of a Flow needed by an end user.
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
	 * Initialize the Flow 
	 * 
	 * @param flow the flow
	 * @param responsabile the flow manager
	 * @param corsiOrigine the flow's starting degree courses
	 * @param certificati the flow's required language certifications
	 * @param listaValutazioni the flow's evaluations
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
	 * Get the manager
	 * @return the flow manager
	 */
	public ResponsabileFlussoBean getResponsabile()
	{
		return responsabile;
	}
	
	/**
	 * Set the manager
	 * @param obj the manager
	 */
	public void setResponsabile(ResponsabileFlussoBean obj)
	{
		this.responsabile = obj;
	}
	
	/**
	 * Get the required language certifications
	 * @return a list of beans representing the certifications
	 */
	public List<CertificatiLinguisticiBean> getCertificati()
	{
		return certificati;
	}
	
	/**
	 * Set the required language certifications
	 * @param obj a list of certificates
	 */
	public void setCertificati(List<CertificatiLinguisticiBean> obj)
	{
		this.certificati = obj;
	}
	
	/**
	 * Get the flow
	 * @return the flow
	 */
	public FlussoBean getFlusso()
	{
		return flow;
	}
	
	/**
	 * Set the flow
	 * @param obj The flow
	 */
	public void setFlusso(FlussoBean obj)
	{
		this.flow = obj;
	}
	
	/**
	 * Get the flow's starting degree courses
	 * @return a list of degree courses
	 */
	public List<CorsoDiLaureaBean> getCorsi()
	{
		return corsiOrigine;
	}
	
	/**
	 * Set the flow starting degree courses
	 * @param obj a list of degree courses
	 */
	public void setCorsi(List<CorsoDiLaureaBean> obj)
	{
		this.corsiOrigine = obj;
	}
	
	/**
	 * Get the flow's evaluations
	 * @return a list of evaluations
	 */
	public List<ValutazioneFlussoBean> getListaValutazioni()
	{
		return listaValutazioni;
	}
	
	/**
	 * Set the flow evaluations
	 * @param obj a list of evaluations
	 */
	public void setListaValutazioni(List<ValutazioneFlussoBean> obj)
	{
		this.listaValutazioni = obj;
	} 
}
