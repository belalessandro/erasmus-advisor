package it.unipd.dei.bding.erasmusadvisor.resources;

import it.unipd.dei.bding.erasmusadvisor.beans.AreaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ArgomentoTesiBean;
import it.unipd.dei.bding.erasmusadvisor.beans.LinguaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ProfessoreBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneTesiBean;





import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Nicola, Luca
 *
 */

public class Thesis 
{
	private ArgomentoTesiBean arg;
	private List<ValutazioneTesiBean> listaValutazioni;
	private List<ProfessoreBean> professori;
	private List<LinguaBean> lingue;
	private List<AreaBean> aree;

	public Thesis(ArgomentoTesiBean arg, List<ValutazioneTesiBean> listaValutazioni,
			List<ProfessoreBean> professori, List<LinguaBean> lingue, List<AreaBean> aree) 
	{
		this.arg = arg;
		this.listaValutazioni = listaValutazioni;
		this.professori = professori;
		this.lingue = lingue;
		this.aree = aree;
	}
	
	public Thesis(ArgomentoTesiBean arg, List<ValutazioneTesiBean> listaValutazioni) 
	{
		this.arg = arg;
		this.listaValutazioni = listaValutazioni;
	}
	
	public Thesis(ArgomentoTesiBean arg) 
	{
		this.arg = arg;
		listaValutazioni = null;
	}
	
	public List<ProfessoreBean> getProfessori()
	{
		return professori;
	}
	
	public void setProfessori(List<ProfessoreBean> obj)
	{
		professori = obj;
	}
	
	public List<LinguaBean> getLingue()
	{
		return lingue;
	}
	
	public void setLingue(List<LinguaBean> obj)
	{
		lingue = obj;
	}
	
	public List<AreaBean> getAree()
	{
		return aree;
	}
	
	public void setAree(List<AreaBean> obj)
	{
		aree = obj;
	}

	/**
	 * Returns the field arg.
	 *
	 * @return the value of argomentoTesi
	 */
	public ArgomentoTesiBean getArgomentoTesi()
	{
		return arg;
	}

	/**
	 * Sets the field arg
	 *
	 * @param universita The value to set
	 */
	public void setArgomentoTesi(ArgomentoTesiBean arg) 
	{
		this.arg = arg;
	}

	/**
	 * Returns the field listaValutazioni.
	 *
	 * @return the value of listaValutazioni
	 */
	public List<ValutazioneTesiBean> getListaValutazioni() 
	{
		return listaValutazioni;
	}

	/**
	 * Sets the field listaValutazioni
	 *
	 * @param listaValutazioni The value to set
	 */
	public void setListaValutazioni(
			ArrayList<ValutazioneTesiBean> listaValutazioni) 
	{
		this.listaValutazioni = listaValutazioni;
	}
	
	/*public String toString()
	{
		String s = arg.getNome()+arg.getNomeUniversita()+arg.getStato()+arg.isTriennale()+arg.isMagistrale()+"";
		return s;
	}*/
}
