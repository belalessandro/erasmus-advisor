package it.unipd.dei.bding.erasmusadvisor.servlets;

public class StudentProfileServlet extends AbstractDatabaseServlet {
	/*
	 * (Autorizzazioni: solo STUDENTE)
	 * 
	 * mappato su /student/profile
	 * 
	 * quando riceve GET
	 * 			-> ritorna su user_profile.jsp tutti i campi collegati allo studente loggato
	 * 
	 * 
	 * quando riceve POST
	 *   		-> Se operazione è "update" modifica i campi relativi allo studente loggato
	 */
}
