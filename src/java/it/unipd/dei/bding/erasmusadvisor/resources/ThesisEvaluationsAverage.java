package it.unipd.dei.bding.erasmusadvisor.resources;

import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneTesiBean;

import java.io.Serializable;
import java.util.List;

/**
 * Compute the average of a thesis' evaluation list.
 * @author Luca
 *
 */
public class ThesisEvaluationsAverage implements Serializable
{
	private static final long serialVersionUID = -7035349945045163292L;
	
	int effortNeeded = 0;
	int subjectAppeal = 0;
	int supervisorAvailability = 0;
	int satisfaction = 0;

	/**
	 * Initializes the class computing the evaluations' average. If the list is void the average is set to 0 for all the fields.
	 * @param list A list of evaluations.
	 */
	public ThesisEvaluationsAverage(List<ValutazioneTesiBean> list)
	{
		int[] sum = new int[4];
		
		if (!list.isEmpty())
		{
			for(ValutazioneTesiBean val : list)
			{
				sum[0] = sum[0] + val.getImpegnoNecessario();
				sum[1] = sum[1] + val.getInteresseArgomento();
				sum[2] = sum[2] + val.getDisponibilitaRelatore();
				sum[3] = sum[3] + val.getSoddisfazione();
			}
			effortNeeded = Math.round(sum[0]/list.size());
			subjectAppeal = Math.round(sum[1]/list.size());
			supervisorAvailability = Math.round(sum[2]/list.size());
			satisfaction = Math.round(sum[3]/list.size());
		}
	}

	/**
	 * Returns the average of the evaluations in the Effort Needed field.
	 * @return The average.
	 */
	public int getEffortNeeded()
	{
		return effortNeeded;
	}

	/**
	 * Returns the average of the evaluations in the Subject Appeal field.
	 * @return The average.
	 */
	public int getSubjectAppeal()
	{
		return subjectAppeal;
	}

	/**
	 * Returns the average of the evaluations in the Supervisor Availability field.
	 * @return The average.
	 */
	public int getSupervisorAvailability()
	{
		return supervisorAvailability;
	}

	/**
	 * Returns the average of the evaluations in the Satisfaction field.
	 * @return The average.
	 */
	public int getSatisfaction()
	{
		return satisfaction;
	}
	
	
}
