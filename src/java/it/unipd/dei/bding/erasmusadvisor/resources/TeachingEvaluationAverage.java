package it.unipd.dei.bding.erasmusadvisor.resources;

import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneInsegnamentoBean;

import java.io.Serializable;
import java.util.List;

/**
 * Compute the average of the a class's evalutation list
 * @author Luca
 *
 */
public class TeachingEvaluationAverage implements Serializable
{
	int teachingQuality = 0;
	int scheduleCompliance = 0;
	int difficulty = 0;
	int interest = 0;

	/**
	 * Initialize the class computing the evaluations' average. If the list is void the average is set to 0 for all the fields.
	 * @param list a list of evaluations
	 */
	public TeachingEvaluationAverage(List<ValutazioneInsegnamentoBean> list)
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

	/**
	 * Get the average of the evaluations in the Teaching Quality field
	 * @return the average
	 */
	public int getTeachingQuality()
	{
		return teachingQuality;
	}

	/**
	 * Get the average of the evaluations in the Schedule Compliance field
	 * @return the average
	 */
	public int getScheduleCompliance()
	{
		return scheduleCompliance;
	}

	/**
	 * Get the average of the evaluations in the Difficulty field
	 * @return the average
	 */
	public int getDifficulty()
	{
		return difficulty;
	}

	/**
	 * Get the average of the evaluations in the Interest field
	 * @return the average
	 */
	public int getInterest()
	{
		return interest;
	}
	
	
}
