
package it.unipd.dei.bding.erasmusadvisor.resources;

import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneUniversitaBean;

import java.io.Serializable;
import java.util.List;

/**
 * Compute the average of a university evalutation list
 * @author Luca
 *
 */
public class UniversityEvaluationsAverage implements Serializable
{
	int urbanLocation = 0;
	int erasmusEvents = 0;
	int teachingsQuality = 0;
	int classroomQuality = 0;
	
	public UniversityEvaluationsAverage(List<ValutazioneUniversitaBean> list)
	{
		int[] sum = new int[4];
		
		if (!list.isEmpty())
		{
			for(ValutazioneUniversitaBean val : list)
			{
				sum[0] = sum[0] + val.getCollocazioneUrbana();
				sum[1] = sum[1] + val.getIniziativeErasmus();
				sum[2] = sum[2] + val.getQtaInsegnamenti();
				sum[3] = sum[3] + val.getQtaAule();
			}
			urbanLocation = Math.round(sum[0]/list.size());
			erasmusEvents = Math.round(sum[1]/list.size());
			teachingsQuality = Math.round(sum[2]/list.size());
			classroomQuality = Math.round(sum[3]/list.size());
		}
	}
	
	public int getUrbanLocation()
	{
		return urbanLocation;
	}
	
	public int getErasmusEvents()
	{
		return erasmusEvents;
	}
	
	public int getTeachingsQuality()
	{
		return teachingsQuality;
	}
	
	public int getClassroomQuality()
	{
		return classroomQuality;
	}
	
	
}

