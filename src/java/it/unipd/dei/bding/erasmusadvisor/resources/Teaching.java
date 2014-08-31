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
 * Contains all the details of a Class displayed an end user (This class has been named 
 * Teaching to avoid conflicts with the standard library Class class.)
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
	 * Initializes the teaching.
	 * 
	 * @param insegnamento The teaching.
	 * @param listaValutazioni The teaching's evaluations.
	 * @param professori The theaching's teachers.
	 * @param lingua The theaching's language.
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
	 * Returns the teaching.
	 * @return A bean representing the teaching.
	 */
	public InsegnamentoBean getInsegnamento()
	{
		return insegnamento;
	}
	
	/**
	 * Sets the teaching.
	 * @param bean The teaching.
	 */
	public void setInsegnamento (InsegnamentoBean bean)
	{
		insegnamento = bean;
	}
	
	/**
	 * Returns the teaching's language.
	 * @return A bean representing the language.
	 */
	public LinguaBean getLingua()
	{
		return lingua;
	}
	
	/**
	 * Sets the teaching's language.
	 * @param bean The language.
	 */
	public void setLingua(LinguaBean bean)
	{
		lingua = bean;
	}
	
	/**
	 * Returns the evaluations to the teaching.
	 * @return A list of beans representing the evaluations.
	 */
	public List<ValutazioneInsegnamentoBean> getValutazioni()
	{
		return listaValutazioni;
	}
	
	/**
	 * Sets the evaluations to the teaching.
	 * @param bean The evaluations.
	 */
	public void setValutazioni(List<ValutazioneInsegnamentoBean> bean)
	{
		listaValutazioni = bean;
	}
	
	/**
	 * Returns the teachers.
	 * @return The teachers.
	 */
	public List<ProfessoreBean> getProfessori()
	{
		return professori;
	}
	
	/**
	 * Sets the teachers.
	 * @param bean A list of teachers.
	 */
	public void setProfessori(List<ProfessoreBean> bean)
	{
		professori = bean;
	}
}


