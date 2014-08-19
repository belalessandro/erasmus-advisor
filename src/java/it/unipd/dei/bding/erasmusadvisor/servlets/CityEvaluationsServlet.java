/**
 * 
 */
package it.unipd.dei.bding.erasmusadvisor.servlets;

/**
 * @author Alessandro
 *
 */
public class CityEvaluationsServlet extends AbstractDatabaseServlet {
	/*
	 * (Autorizzazioni: GET NESSUNO, 
	 * 					POST SOLO Resp.Flusso)
	 * 
	 * mappato su /city/evaluations
	 * 
	 * quando riceve GET
	 * 			-> Redirect su /city/list
	 * 
	 * quando riceve POST
	 *   		-> Se operazione è "remove" rimuove la valutazione con nomeutente+nomecitta+statocitta passato per parametro
	 */
}
