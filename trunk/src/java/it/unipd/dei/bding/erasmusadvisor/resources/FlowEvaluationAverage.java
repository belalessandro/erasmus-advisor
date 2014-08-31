package it.unipd.dei.bding.erasmusadvisor.resources;

import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneFlussoBean;

import java.io.Serializable;
import java.util.List;

/**
 * Compute the average of the a flow's evaluations list.
 * @author Luca
 *
 */
public class FlowEvaluationAverage implements Serializable
{
	private static final long serialVersionUID = -6537613055867808200L;
	
	int gratification = 0;
	int academicFulfillment = 0;
	int didactics = 0;
	int managerEvaluation = 0;

	/**
	 * Initializes the class computing the evaluations' average. If the list is void the average is set to 0 for all the fields.
	 * @param list A list of evaluations.
	 */
	public FlowEvaluationAverage(List<ValutazioneFlussoBean> list)
	{
		int[] sum = new int[4];
		
		if (!list.isEmpty())
		{
			for(ValutazioneFlussoBean val : list)
			{
				sum[0] = sum[0] + val.getSoddEsperienza();
				sum[1] = sum[1] + val.getSoddAccademica();
				sum[2] = sum[2] + val.getDidattica();
				sum[3] = sum[3] + val.getValutazioneResponsabile();
			}
			gratification = Math.round(sum[0]/list.size());
			academicFulfillment = Math.round(sum[1]/list.size());
			didactics = Math.round(sum[2]/list.size());
			managerEvaluation = Math.round(sum[3]/list.size());
		}
	}

	/**
	 * Returns the average of the evaluations in the Gratification field.
	 * @return The average.
	 */
	public int getGratification()
	{
		return gratification;
	}

	/**
	 * Returns the average of the evaluations in the Academic Fulfillment field.
	 * @return The average.
	 */
	public int getAcademicFulfillment()
	{
		return academicFulfillment;
	}

	/**
	 * Returns the average of the evaluations in the Didactics field.
	 * @return The average.
	 */
	public int getDidactics()
	{
		return didactics;
	}

	/**
	 * Returns the average of the evaluations in the Manager Evaluation field.
	 * @return The average.
	 */
	public int getManagerEvaluation()
	{
		return managerEvaluation;
	}
	
	
}
