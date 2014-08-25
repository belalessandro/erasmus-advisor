package it.unipd.dei.bding.erasmusadvisor.servlets.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Filter for solving some encoding problems 
 * and setting the UTF-8 encode
 * (overriding the default configuration) 
 * 
 * @author Alessandro
 *
 */
public class CharacterEncodingFilter implements Filter {

	    @Override
	    public void init(FilterConfig filterConfig)
	            throws ServletException {

	    }

	    @Override
	    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
	            throws IOException, ServletException {
	        servletRequest.setCharacterEncoding("UTF-8");
	        //servletResponse.setContentType("text/html; charset=UTF-8");
	        servletResponse.setCharacterEncoding("UTF-8"); // this is required 
	        filterChain.doFilter(servletRequest, servletResponse);
	    }

	    @Override
	    public void destroy() {

	    }
}
