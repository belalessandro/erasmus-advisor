package it.unipd.dei.bding.erasmusadvisor.resources;

import java.util.List;

import it.unipd.dei.bding.erasmusadvisor.beans.CertificatiLinguisticiBean;
import it.unipd.dei.bding.erasmusadvisor.beans.CorsoDiLaureaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.FlussoBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ResponsabileFlussoBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneFlussoBean;

public class Flow 
{
	private FlussoBean flow;
	private ResponsabileFlussoBean responsabile;
	private List<CorsoDiLaureaBean> corsiOrigine;
	private List<CertificatiLinguisticiBean> certificati;
	private List<ValutazioneFlussoBean> listaValutazioni;
	
	public Flow(FlussoBean flow, ResponsabileFlussoBean responsabile, List<CorsoDiLaureaBean> corsiOrigine, 
			List<CertificatiLinguisticiBean> certificati, List<ValutazioneFlussoBean> listaValutazioni)
	{
		this.certificati = certificati;
		this.responsabile = responsabile;
		this.flow = flow;
		this.corsiOrigine = corsiOrigine;
		this.listaValutazioni = listaValutazioni;
	}
	
	public ResponsabileFlussoBean getResponsabile()
	{
		return responsabile;
	}
	
	public void setResponsabile(ResponsabileFlussoBean obj)
	{
		this.responsabile = obj;
	}
	
	
	public List<CertificatiLinguisticiBean> getCertificati()
	{
		return certificati;
	}
	
	public void setCertificati(List<CertificatiLinguisticiBean> obj)
	{
		this.certificati = obj;
	}
	
	public FlussoBean getFlusso()
	{
		return flow;
	}
	
	public void setFlusso(FlussoBean obj)
	{
		this.flow = obj;
	}
	
	public List<CorsoDiLaureaBean> getCorsi()
	{
		return corsiOrigine;
	}
	
	public void setCorsi(List<CorsoDiLaureaBean> obj)
	{
		this.corsiOrigine = obj;
	}
	
	public List<ValutazioneFlussoBean> getListaValutazioni()
	{
		return listaValutazioni;
	}
	
	public void setListaValutazioni(List<ValutazioneFlussoBean> obj)
	{
		this.listaValutazioni = obj;
	} 
}
