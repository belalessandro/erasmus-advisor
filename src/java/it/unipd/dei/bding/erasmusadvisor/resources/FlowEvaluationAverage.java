package it.unipd.dei.bding.erasmusadvisor.resources;

import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneFlussoBean;

import java.io.Serializable;
import java.util.List;

/**
 * Compute the average of the a class's Flow list
 * @author Luca
 *
 */
public class FlowEvaluationAverage implements Serializable
{
	int gratification = 0;
	int academicFulfillment = 0;
	int didactics = 0;
	int managerEvaluation = 0;
	
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
	
	public int getGratification()
	{
		return gratification;
	}
	
	public int getAcademicFulfillment()
	{
		return academicFulfillment;
	}
	
	public int getDidactics()
	{
		return didactics;
	}
	
	public int getManagerEvaluation()
	{
		return managerEvaluation;
	}
	
	
}
