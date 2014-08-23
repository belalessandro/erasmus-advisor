package it.unipd.dei.bding.erasmusadvisor.resources;

import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneCittaBean;

import java.io.Serializable;
import java.util.List;

/**
 * Compute the average of the a city's evalutation list
 * @author Luca
 *
 */
public class CityEvaluationsAverage implements Serializable
{
	int costOfLife = 0;
	int houseAvailability = 0;
	int liveability = 0;
	int socialLife = 0;
	
	/**
	 * Initialize the class computing the evaluations' average. If the list is void the average is set to 0 for all the fields.
	 * @param list a list of evaluations
	 */
	public CityEvaluationsAverage(List<ValutazioneCittaBean> list)
	{
		int[] sum = new int[4];
		
		if (!list.isEmpty())
		{
			for(ValutazioneCittaBean val : list)
			{
				sum[0] = sum[0] + val.getCostoDellaVita();
				sum[1] = sum[1] + val.getDisponibilitaAlloggi();
				sum[2] = sum[2] + val.getVivibilitaUrbana();
				sum[3] = sum[3] + val.getVitaSociale();
			}
			costOfLife = Math.round(sum[0]/list.size());
			houseAvailability = Math.round(sum[1]/list.size());
			liveability = Math.round(sum[2]/list.size());
			socialLife = Math.round(sum[3]/list.size());
		}
	}

	/**
	 * Get the average of the evaluations in the Cost of Life field
	 * @return the average
	 */
	public int getCostOfLife()
	{
		return costOfLife;
	}

	/**
	 * Get the average of the evaluations in the House Availability field
	 * @return the average
	 */
	public int getHouseAvailability()
	{
		return houseAvailability;
	}

	/**
	 * Get the average of the evaluations in the Liveability field
	 * @return the average
	 */
	public int getLiveability()
	{
		return liveability;
	}
	
	/**
	 * Get the average of the evaluations in the Social Life field
	 * @return the average
	 */
	public int getSocialLife()
	{
		return socialLife;
	}
	
	
}
