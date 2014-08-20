package it.unipd.dei.bding.erasmusadvisor.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Login extends HttpServlet {

    
    /**
	 * CLASSE DI ESEMPIO: Potrebbe essere necessaria una seria revision
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String email = request.getParameter("email");
        String pass = request.getParameter("pass");
        
        try {
        	//Qui forse si passa da metodo statico a non statico
			if(Validate.checkUser(email, pass)==0)
			{
			    RequestDispatcher rs = request.getRequestDispatcher("Welcome");
			    rs.forward(request, response);
			}
			else
			{
			   out.println("Username or Password incorrect");
			   RequestDispatcher rs = request.getRequestDispatcher("index.html");
			   rs.include(request, response);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block: cosa succede in caso di errore SQL?
			e.printStackTrace();
		}
       
    }  
}