/**
 * 
 */
package it.unipd.dei.bding.erasmusadvisor.resources;

import java.util.List;

import it.unipd.dei.bding.erasmusadvisor.beans.InsegnamentoBean;
import it.unipd.dei.bding.erasmusadvisor.beans.LinguaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ProfessoreBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneInsegnamentoBean;

/**
 * @author Luca
 *
 */
public class Teaching 
{
	private InsegnamentoBean insegnamento;
	private List<ValutazioneInsegnamentoBean> listaValutazioni;
	private List<ProfessoreBean> professori;
	private LinguaBean lingua;
	
	public Teaching(InsegnamentoBean insegnamento, List<ValutazioneInsegnamentoBean> listaValutazioni, 
			List<ProfessoreBean> professori, LinguaBean lingua)

	{
		this.lingua = lingua;
		this.insegnamento = insegnamento;
		this.listaValutazioni = listaValutazioni;
		this.professori = professori; 
	}
	
	public InsegnamentoBean getInsegnamento()
	{
		return insegnamento;
	}
	
	public void setInsegnamento (InsegnamentoBean bean)
	{
		insegnamento = bean;
	}
	
	public LinguaBean getLingua()
	{
		return lingua;
	}
	
	public void setLingua(LinguaBean bean)
	{
		lingua = bean;
	}
	
	public List<ValutazioneInsegnamentoBean> getValutazioni()
	{
		return listaValutazioni;
	}
	
	public void setValutazioni(List<ValutazioneInsegnamentoBean> bean)
	{
		listaValutazioni = bean;
	}
	
	public List<ProfessoreBean> getProfessori()
	{
		return professori;
	}
	
	public void setProfessori(List<ProfessoreBean> bean)
	{
		professori = bean;
	}
}


