package it.unipd.dei.bding.erasmusadvisor.resources;

import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneInsegnamentoBean;

import java.io.Serializable;
import java.util.List;

/**
 * Compute the average of the a class's evalutation list
 * @author Luca
 *
 */
public class ClassEvaluationAverage implements Serializable
{
	int teachingQuality = 0;
	int scheduleCompliance = 0;
	int difficulty = 0;
	int interest = 0;
	
	public ClassEvaluationAverage(List<ValutazioneInsegnamentoBean> list)
	{
		int[] sum = new int[4];
		
		if (!list.isEmpty())
		{
			for(ValutazioneInsegnamentoBean val : list)
			{
				sum[0] = sum[0] + val.getQtaInsegnamanto();
				sum[1] = sum[1] + val.getRispettoDelleOre();
				sum[2] = sum[2] + val.getDifficolta();
				sum[3] = sum[3] + val.getInteresse();
			}
			teachingQuality = Math.round(sum[0]/list.size());
			scheduleCompliance = Math.round(sum[1]/list.size());
			difficulty = Math.round(sum[2]/list.size());
			interest = Math.round(sum[3]/list.size());
		}
	}
	
	public int getTeachingQuality()
	{
		return teachingQuality;
	}
	
	public int getScheduleCompliance()
	{
		return scheduleCompliance;
	}
	
	public int getDifficulty()
	{
		return difficulty;
	}
	
	public int getInterest()
	{
		return interest;
	}
	
	
}
