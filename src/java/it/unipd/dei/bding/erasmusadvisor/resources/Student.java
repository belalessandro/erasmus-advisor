package it.unipd.dei.bding.erasmusadvisor.resources;

import it.unipd.dei.bding.erasmusadvisor.beans.CorsoDiLaureaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.IscrizioneBean;
import it.unipd.dei.bding.erasmusadvisor.beans.StudenteBean;

/**
 * Contains all the details of a Student needed by an end user.
 * 
 * @author Luca
 *
 */

public class Student 
{
	StudenteBean studente;
	IscrizioneBean corso;
	CorsoDiLaureaBean corsodilaurea;
	
	/**
	 * Initialize the Student
	 * @param studente the student
	 * @param corso the degree course the student follows
	 */
	public Student(StudenteBean studente, IscrizioneBean corso, CorsoDiLaureaBean corsodilaurea)
	{
		this.corso = corso;
		this.studente = studente;
		this.corsodilaurea = corsodilaurea;
	}
	
	/**
	 * Returns the degree course
	 * @return the degree course
	 */
	public CorsoDiLaureaBean getCorso()
	{
		return corsodilaurea;
	}
	
	/**
	 * sets the degree course
	 * @param obj the degree course
	 */
	public void setCorso(CorsoDiLaureaBean obj)
	{
		this.corsodilaurea = obj;
	}
	
	/**
	 * Returns the student
	 * @return the student
	 */
	public StudenteBean getStudente()
	{
		return studente;
	}
	
	/**
	 * Set the student
	 * @param obj a bean representing the student
	 */
	public void setStudente(StudenteBean obj)
	{
		this.studente = obj;
	}
	
	/**
	 * Returns the degree course
	 * @return the degree course
	 */
	public IscrizioneBean getIscrizione()
	{
		return corso;
	}
	
	/**
	 * sets the degree course
	 * @param obj the degree course
	 */
	public void setIscrizione(IscrizioneBean obj)
	{
		this.corso = obj;
	}

}
