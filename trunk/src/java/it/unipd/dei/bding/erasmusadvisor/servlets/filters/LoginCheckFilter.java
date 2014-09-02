package it.unipd.dei.bding.erasmusadvisor.servlets.filters;

import it.unipd.dei.bding.erasmusadvisor.resources.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Filter for restrict the access to logged user only
 * 
 * @author Alessandro
 * 
 */
public class LoginCheckFilter implements Filter {
	/**
	 * Variable to check if it is enabled
	 */
	private boolean filterEnabled;

	/**
	 * Stores the patterns to be excluded
	 */
	private ArrayList<String> excludePatterns;
	
	/**
	 * Checks if the user is logged
	 * 
	 * @param req 
	 * 		the servlet request
	 * @param res 
	 * 		the servlet response
	 * @param chain 
	 * 		the filter chain
	 * 
	 */
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		
		// Casting for HTTP Servlets
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		// Get the current session (if exists)
		HttpSession session = request.getSession(false);
		
		String urlWithPath = request.getRequestURI().toString();

		if (!matchExcludePatterns(urlWithPath) && filterEnabled
				&& (session == null || session.getAttribute("loggedUser") == null)) {
			
	    	Message m = new Message("User not logged.","E100", 
	    			"You seem not logged or session is expired. You must do the login.");
	    	request.setAttribute("message", m);
	    	errorForward(request, response);
		} else {
			chain.doFilter(req, res); // Logged-in user found, so just continue request.
		}
	}

	/**
	 * Checks if the URL is excluded from access restriction
	 * @param url the address
	 * @return true if it is excluded
	 */
	private boolean matchExcludePatterns(String url) {
		for (String pattern : excludePatterns) {
			boolean matches = false;
			
			if (pattern.startsWith("*")) {

				matches = url.endsWith(pattern.substring(1));
				
			} else if (pattern.endsWith("*")) {
			
				matches = url.startsWith(pattern.substring(0, pattern.length()-1));
			
			} else { // checks for exact match
				
				matches = url.equals(pattern);
			}
			
			if (matches) {
				return true; // Found
			}
		}
		return false; // Not Found
	}

	@Override
	public void destroy() {

	}

	/**
	 * Init parameters for the filter
	 * 
	 */
	@Override
	public void init(FilterConfig cfg) throws ServletException {
		// Gets the parameter for disabling or enabling the filter
		String enabled = cfg.getInitParameter("enabled");
		filterEnabled = enabled.equalsIgnoreCase("true") ? true : false;

		// Gets the excluded patterns
		String s = cfg.getInitParameter("excludePatterns");
		StringTokenizer st = new StringTokenizer(s, ";");

		// add the patterns to the list
		ArrayList<String> list = new ArrayList<String>();
		while (st.hasMoreTokens()) {
			list.add(st.nextToken());
		}

		this.excludePatterns = list;
	}
	
    
    /**
     * Handles error forwarding between pages.
     * 
	 * @param request 
	 * 				request from the client
	 * @param response 
	 * 				response to the client 
	 * @throws ServletException
	 * 			 	if any error occurs while executing the servlet
	 * @throws IOException
	 *  			if any error occurs in the client/server communication.
	 */
    private void errorForward(HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException  {
    	// Error management
    		
    	request.getServletContext().getRequestDispatcher("/jsp/error.jsp")
    		.forward(request, response); // ERROR PAGE
    }
}
