/**
 * 
 */
package it.unipd.dei.bding.erasmusadvisor.servlets;

/**
 * @author Alessandro
 *
 */
public class CityServlet extends AbstractDatabaseServlet {
	/*
	 * (Autorizzazioni: GET Tutti, 
	 * 					POST SOLO Resp.Flusso)
	 * 
	 * mappato su /city
	 * 
	 * quando riceve GET
	 * 			-> Se c'è un id restituisce e visualizza la citta' su show_city.jsp
	 * 			-> altrimenti: se è resp.flusso 
	 * 						-> mostra il form di insert_city.jsp 
	 * 						-> altrimenti: redirect su /city/list
	 * 				
	 * 
	 * 
	 * quando riceve POST
	 *   		-> Se operazione è "remove" rimuove l'interesse collegato allo studente loggato e IdFlusso come parametro
	 *   		-> Se operazione è "insert" inserisce l'interesse collegato allo studente loggato e IdFlusso come parametro
	 */
}
