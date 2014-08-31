package it.unipd.dei.bding.erasmusadvisor.resources;

import java.io.Serializable;

/**
 * Represents a logged user.
 * 
 * @author Alessandro
 * @version 1.1
 */
public class LoggedUser implements Serializable 
{

	private static final long serialVersionUID = -3768523744321447192L;

	/**
	 * Stores the authorization level
	 */
	public final UserType auth;
	
	/**
	 * Stores the username
	 */
	private final String user;

	
	/**
	 * Creates a model for a logged user, specifying an authorization level.
	 * @param auth The user type.
	 * @param user The user name.
	 */
	public LoggedUser(final UserType auth, final String user) {
		super();
		this.auth = auth;
		this.user = user;
	}

	/**
	 * Checks if the logged user is a Student.
	 *
	 * @return {@code true} if the logged user is a Student.
	 */
	public boolean isStudent() {
		return auth.equals(UserType.STUDENTE);
	}

	/**
	 * Checks if the logged user is a Flow Responsible.
	 *
	 * @return {@code true} if the logged user is a Flow Manager.
	 */
	public boolean isFlowResp() {
		return auth.equals(UserType.RESPONSABILE);
	}

	/**
	 * Checks if the logged user is a Coordinator.
	 *
	 * @return {@code true} if the logged user is an Erasmus Coordinator.
	 */
	public boolean isCoord() {
		return auth.equals(UserType.COORDINATORE);
	}

	/**
	 * Returns the user name.
	 *
	 * @return The user name.
	 */
	public String getUser() {
		return user;
	}
	
}