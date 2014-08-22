package it.unipd.dei.bding.erasmusadvisor.resources;

import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneTesiBean;

import java.io.Serializable;
import java.util.List;

/**
 * Compute the average of a thesis' evalutation list
 * @author Luca
 *
 */
public class ThesisEvaluationsAverage implements Serializable
{
	int effortNeeded = 0;
	int subjectAppeal = 0;
	int supervisorAvailability = 0;
	int satisfaction = 0;
	
	public ThesisEvaluationsAverage(List<ValutazioneTesiBean> list)
	{
		int[] sum = new int[4];
		
		if (!list.isEmpty())
		{
			for(ValutazioneTesiBean val : list)
			{
				sum[0] = sum[0] + val.getImpegnoNecessario();
				sum[1] = sum[1] + val.getInteresseArgomento();
				sum[2] = sum[2] + val.getDiponibilitaRelatore();
				sum[3] = sum[3] + val.getSoddisfazione();
			}
			effortNeeded = Math.round(sum[0]/list.size());
			subjectAppeal = Math.round(sum[1]/list.size());
			supervisorAvailability = Math.round(sum[2]/list.size());
			satisfaction = Math.round(sum[3]/list.size());
		}
	}
	
	public int getEffortNeeded()
	{
		return effortNeeded;
	}
	
	public int getSubjectAppeal()
	{
		return subjectAppeal;
	}
	
	public int getSupervisorAvailability()
	{
		return supervisorAvailability;
	}
	
	public int getSatisfaction()
	{
		return satisfaction;
	}
	
	
}
