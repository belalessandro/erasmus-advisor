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
 * Contains all the details of a Class needed by an end user. Named Teaching to avoid conflicts with the standard library Class class.
 * 
 * @author Luca
 *
 */
public class Teaching 
{
	private InsegnamentoBean insegnamento;
	private List<ValutazioneInsegnamentoBean> listaValutazioni;
	private List<ProfessoreBean> professori;
	private LinguaBean lingua;
	
	/**
	 * Initialize the Teaching
	 * 
	 * @param insegnamento the Teaching
	 * @param listaValutazioni the teaching's evaluations
	 * @param professori the theaching's teachers
	 * @param lingua the theaching's language
	 */
	public Teaching(InsegnamentoBean insegnamento, List<ValutazioneInsegnamentoBean> listaValutazioni, 
			List<ProfessoreBean> professori, LinguaBean lingua)

	{
		this.lingua = lingua;
		this.insegnamento = insegnamento;
		this.listaValutazioni = listaValutazioni;
		this.professori = professori; 
	}
	
	/**
	 * Get the teaching
	 * @return a bean representing the teaching
	 */
	public InsegnamentoBean getInsegnamento()
	{
		return insegnamento;
	}
	
	/**
	 * Set the teaching
	 * @param bean the teaching
	 */
	public void setInsegnamento (InsegnamentoBean bean)
	{
		insegnamento = bean;
	}
	
	/**
	 * Get the teaching's language
	 * @return a bean representing the language
	 */
	public LinguaBean getLingua()
	{
		return lingua;
	}
	
	/**
	 * Set the teaching's language
	 * @param bean the language
	 */
	public void setLingua(LinguaBean bean)
	{
		lingua = bean;
	}
	
	/**
	 * Get the evaluations to the teaching
	 * @return a list of beans representing the evaluations
	 */
	public List<ValutazioneInsegnamentoBean> getValutazioni()
	{
		return listaValutazioni;
	}
	
	/**
	 * Set the evaluations to the teaching
	 * @param bean the evaluations
	 */
	public void setValutazioni(List<ValutazioneInsegnamentoBean> bean)
	{
		listaValutazioni = bean;
	}
	
	/**
	 * Get the teachers
	 * @return the teachers
	 */
	public List<ProfessoreBean> getProfessori()
	{
		return professori;
	}
	
	/**
	 * Set the teachers
	 * @param bean a list of teachers
	 */
	public void setProfessori(List<ProfessoreBean> bean)
	{
		professori = bean;
	}
}


