package it.unipd.dei.bding.erasmusadvisor.resources;

import it.unipd.dei.bding.erasmusadvisor.beans.CorsoDiLaureaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.IscrizioneBean;
import it.unipd.dei.bding.erasmusadvisor.beans.StudenteBean;

/**
 * Contains all the details of a Student displayed to an end user.
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
	 * Initialize the Student.
	 * @param studente The student.
	 * @param corso The degree course the student follows.
	 */
	public Student(StudenteBean studente, IscrizioneBean corso, CorsoDiLaureaBean corsodilaurea)
	{
		this.corso = corso;
		this.studente = studente;
		this.corsodilaurea = corsodilaurea;
	}
	
	/**
	 * Returns the degree course.
	 * @return The degree course.
	 */
	public CorsoDiLaureaBean getCorso()
	{
		return corsodilaurea;
	}
	
	/**
	 * Sets the degree course.
	 * @param obj The degree course.
	 */
	public void setCorso(CorsoDiLaureaBean obj)
	{
		this.corsodilaurea = obj;
	}
	
	/**
	 * Returns the student.
	 * @return The student.
	 */
	public StudenteBean getStudente()
	{
		return studente;
	}
	
	/**
	 * Sets the student.
	 * @param obj A bean representing the student.
	 */
	public void setStudente(StudenteBean obj)
	{
		this.studente = obj;
	}
	
	/**
	 * Returns the degree course.
	 * @return The degree course.
	 */
	public IscrizioneBean getIscrizione()
	{
		return corso;
	}
	
	/**
	 * Sets the degree course.
	 * @param obj The degree course.
	 */
	public void setIscrizione(IscrizioneBean obj)
	{
		this.corso = obj;
	}

}
