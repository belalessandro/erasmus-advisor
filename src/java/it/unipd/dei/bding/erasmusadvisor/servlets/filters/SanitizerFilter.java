package it.unipd.dei.bding.erasmusadvisor.servlets.filters;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;

/**
 * Filter for sanitizing input
 * 
 * @author Fede
 * 
 */
public class SanitizerFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		PolicyFactory policy = Sanitizers.FORMATTING.and(Sanitizers.LINKS);
		Enumeration<String> names = servletRequest.getAttributeNames();
		for (String name=names.nextElement(); names.hasMoreElements();) {
			Object par=servletRequest.getAttribute(name);
			if(par instanceof String){
				par=policy.sanitize((String)par);
			}
			servletRequest.setAttribute(name, par);
		}
		filterChain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void destroy() {

	}
}
