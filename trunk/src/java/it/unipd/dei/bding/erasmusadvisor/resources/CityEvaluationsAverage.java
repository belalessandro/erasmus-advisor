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
	
	public int getCostOfLife()
	{
		return costOfLife;
	}
	
	public int getHouseAvailability()
	{
		return houseAvailability;
	}
	
	public int getLiveability()
	{
		return liveability;
	}
	
	public int getSocialLife()
	{
		return socialLife;
	}
	
	
}
