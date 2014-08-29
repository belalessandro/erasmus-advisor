package it.unipd.dei.bding.erasmusadvisor.resources;

import it.unipd.dei.bding.erasmusadvisor.beans.ArgomentoTesiBean;
import it.unipd.dei.bding.erasmusadvisor.beans.InsegnamentoBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ProfessoreBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ResponsabileFlussoBean;

import java.util.List;



/**
 * Represents all notifications showed to a coordinator.
 * 
 * @author mauro
 *
 */
public class Notifications 
{
	private List<ResponsabileFlussoBean> responsabiliFlusso;
	private List<InsegnamentoBean> insegnamenti;
	private List<List<ProfessoreBean>> professoriInsegnamento;
	private List<ArgomentoTesiBean> argomentiTesi;
	private List<List<ProfessoreBean>> professoriTesi;
	
	public Notifications() {
		
	}
	
	public Notifications(List<ResponsabileFlussoBean> responsabiliFlusso, 
			List<InsegnamentoBean> insegnamenti, 
			List<List<ProfessoreBean>> professoriInsegnamento,
			List<ArgomentoTesiBean> argomentiTesi,
			List<List<ProfessoreBean>> professoriTesi) {
		
		this.responsabiliFlusso = responsabiliFlusso;
		this.insegnamenti = insegnamenti;
		this.professoriInsegnamento = professoriInsegnamento;
		this.professoriTesi = professoriTesi;
		this.argomentiTesi = argomentiTesi;
	}
	
	/**
	 * Get a list of flow manager that need to be checked.
	 * @return list of beans of flow managers
	 */
	public List<ResponsabileFlussoBean> getResponsabiliFlusso() { return responsabiliFlusso; }
	
	/**
	 * Set the list of flow managers.
	 * @param list of beans of flow managers
	 */
	public void setResponsabiliFlusso(List<ResponsabileFlussoBean> responsabili) {
		this.responsabiliFlusso = responsabili;
	}
	
	/**
	 * Get a list of classes that need to be checked.
	 * @return list of beans of classes
	 */
	public List<InsegnamentoBean> getInsegnamenti() { return insegnamenti; }
	
	/**
	 * Set the list of classes.
	 * @param list of beans of classes
	 */
	public void setInsegnamenti(List<InsegnamentoBean> insegnamenti) {
		this.insegnamenti = insegnamenti;
	}
	
	/**
	 * Get a list of thesis that need to be checked.
	 * @return list of beans of thesis
	 */
	public List<ArgomentoTesiBean> getArgomentiTesi() { return argomentiTesi; }
	
	/**
	 * Set the list of thesis.
	 * @param list of beans of thesis
	 */
	public void setArgomentiTesi(List<ArgomentoTesiBean> argomenti) {
		this.argomentiTesi = argomenti;
	}
	
	/**
	 * Get the list of class's professors
	 * @return the list of class's professors
	 */
	public List<List<ProfessoreBean>> getProfessoriInsegnamenti() { return professoriInsegnamento; }
	
	/**
	 * Get the list of class's professors
	 * @param professoriInsegnamento the list of class's professors
	 */
	public void setProfessoriInsegnamenti(List<List<ProfessoreBean>> professoriInsegnamento)
	{
		this.professoriInsegnamento = professoriInsegnamento;
	}
	
	/**
	 * Get the list of thesis' professors
	 * @return the list of thesis' professors
	 */
	public List<List<ProfessoreBean>> getProfessoriTesi() { return professoriTesi; }
	
	/**
	 * Set the list of thesis' professors
	 * @param professoriTesi the list of thesis' professors
	 */
	public void setProfessoriTesi(List<List<ProfessoreBean>> professoriTesi) 
	{
		this.professoriTesi = professoriTesi;
	}
	
	

}
