
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
	private static final long serialVersionUID = 3354783428157891794L;
	
	int urbanLocation = 0;
	int erasmusEvents = 0;
	int teachingsQuality = 0;
	int classroomQuality = 0;

	/**
	 * Initialize the class computing the evaluations' average. If the list is void the average is set to 0 for all the fields.
	 * @param list a list of evaluations
	 */
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

	/**
	 * Get the average of the evaluations in the Urban Location field
	 * @return the average
	 */
	public int getUrbanLocation()
	{
		return urbanLocation;
	}

	/**
	 * Get the average of the evaluations in the Erasmus Events field
	 * @return the average
	 */
	public int getErasmusEvents()
	{
		return erasmusEvents;
	}

	/**
	 * Get the average of the evaluations in the Teachings Quality field
	 * @return the average
	 */
	public int getTeachingsQuality()
	{
		return teachingsQuality;
	}

	/**
	 * Get the average of the evaluations in the Classroom Quality field
	 * @return the average
	 */
	public int getClassroomQuality()
	{
		return classroomQuality;
	}
	
	
}

