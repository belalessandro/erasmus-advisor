
package it.unipd.dei.bding.erasmusadvisor.resources;

import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneUniversitaBean;

import java.io.Serializable;
import java.util.List;

/**
 * ComputeS the average of a university evaluation list.
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
	 * Initializes the class computing the evaluations' average. If the list is void the average is set to 0 for all the fields.
	 * @param list A list of evaluations.
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
	 * Returns the average of the evaluations in the Urban Location field.
	 * @return The average.
	 */
	public int getUrbanLocation()
	{
		return urbanLocation;
	}

	/**
	 * Returns the average of the evaluations in the Erasmus Events field.
	 * @return The average.
	 */
	public int getErasmusEvents()
	{
		return erasmusEvents;
	}

	/**
	 * Returns the average of the evaluations in the Teachings Quality field.
	 * @return The average.
	 */
	public int getTeachingsQuality()
	{
		return teachingsQuality;
	}

	/**
	 * Returns the average of the evaluations in the Classroom Quality field.
	 * @return The average.
	 */
	public int getClassroomQuality()
	{
		return classroomQuality;
	}
	
	
}

