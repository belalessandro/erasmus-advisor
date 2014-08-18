package it.unipd.dei.bding.erasmusadvisor.resources;

/**
 * Represents a logged user.
 * 
 * @author Alessandro
 * @version 1.1
 */
public class LoggedUser {
	/**
	 * Constants for the authorization level
	 */
	public final static String AUTH_STUDENT = "Studente";
	public final static String AUTH_FLOWRESP = "ResponsabileFlusso";
	public final static String AUTH_COORD = "Coordinatore";
	
	/**
	 * Stores the authorization level
	 */
	private final String auth;
	
	/**
	 * Stores the username
	 */
	private final String user;

	
	/**
	 * Creates a model for a logged user, specifying an authorization level
	 * @param auth LoggedUser.AUTH_STUDENT, LoggedUser.AUTH_FLOWRESP or LoggedUser.AUTH_COORD
	 */
	public LoggedUser(final String auth, final String user) {
		super();
		this.auth = auth;
		this.user = user;
	}


	/**
	 * Checks if the logged user is a Student.
	 *
	 * @return true if the logged user is a Student
	 */
	public boolean isStudent() {
		return auth.equals(AUTH_STUDENT);
	}


	/**
	 * Checks if the logged user is a Flow Responsible.
	 *
	 * @return true if the logged user is a Flow Responsible
	 */
	public boolean isFlowResp() {
		return auth.equals(AUTH_FLOWRESP);
	}


	/**
	 * Checks if the logged user is a Coordinator.
	 *
	 * @return true if the logged user is a Coordinator
	 */
	public boolean isCoord() {
		return auth.equals(AUTH_COORD);
	}


	/**
	 * Returns the field user.
	 *
	 * @return the value of user
	 */
	public String getUser() {
		return user;
	}
	
	
}

///**
// * Represents a logged user.
// * 
// * @author Alessandro
// * @version 1.00
// */
//public class LoggedUser {
//	
//	/**
//	 * If the logged user has authorization level: Studente 
//	 */
//	private boolean isStudent;
//
//	
//	/**
//	 * If the logged user has authorization level: ResponsabileFlusso 
//	 */
//	private boolean isFlowResp;
//
//	
//	/**
//	 * If the logged user has authorization level: Coordinatore 
//	 */
//	private boolean isCoord;
//
//
//	/**
//	 * @param isStudent
//	 * @param isFlowResp
//	 * @param isCoord
//	 */
//	public LoggedUser(boolean isStudent, boolean isFlowResp, boolean isCoord) {
//		super();
//		this.isStudent = isStudent;
//		this.isFlowResp = isFlowResp;
//		this.isCoord = isCoord;
//	}
//
//
//	/**
//	 * Returns the field isStudent.
//	 *
//	 * @return the value of isStudent
//	 */
//	public boolean isStudent() {
//		return isStudent;
//	}
//
//
//	/**
//	 * Sets the field isStudent
//	 *
//	 * @param isStudent The value to set
//	 */
//	public void setStudent(boolean isStudent) {
//		this.isStudent = isStudent;
//	}
//
//
//	/**
//	 * Returns the field isFlowResp.
//	 *
//	 * @return the value of isFlowResp
//	 */
//	public boolean isFlowResp() {
//		return isFlowResp;
//	}
//
//
//	/**
//	 * Sets the field isFlowResp
//	 *
//	 * @param isFlowResp The value to set
//	 */
//	public void setFlowResp(boolean isFlowResp) {
//		this.isFlowResp = isFlowResp;
//	}
//
//
//	/**
//	 * Returns the field isCoord.
//	 *
//	 * @return the value of isCoord
//	 */
//	public boolean isCoord() {
//		return isCoord;
//	}
//
//
//	/**
//	 * Sets the field isCoord
//	 *
//	 * @param isCoord The value to set
//	 */
//	public void setCoord(boolean isCoord) {
//		this.isCoord = isCoord;
//	}
//	
//	
//}
