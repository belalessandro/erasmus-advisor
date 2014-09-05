package it.unipd.dei.bding.erasmusadvisor.resources;

/**
 * Represents an error message.
 * 
 */
public class Message {

	/**
	 * The message
	 */
	private final String message;
	
	
	/**
	 * The code of the error, if any
	 */
	private final String errorCode;


	/**
	 * Additional details about the error, if any
	 */
	private final String errorDetails;
	
	/**
	 * Indicates whether the message is about an error or not.
	 */
	private final boolean isError;


	/**
	 * Creates an error message.
	 * 
	 * @param errorCode The code of the error.
	 * @param message The error message.
	 * @param errorDetails Additional details about the error.
	 */
	public Message(final String message, final String errorCode, final String errorDetails) {
		this.message = message;
		this.errorCode = errorCode;
		this.errorDetails = errorDetails;
		this.isError = true;
	}


	/**
	 * Creates a message.
	 * 
	 * @param message The message.
	 */
	public Message(final String message) {
		this.message = message;
		this.errorCode = null;
		this.errorDetails = null;
		this.isError = false;
	}


	/**
	 * Returns the message.
	 * 
	 * @return The message.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Returns the code of the error, if any.
	 * 
	 * @return The code of the error, if any.
	 */
	public String getErrorCode() {
		return errorCode;
	}
	
	/**
	 * Returns additional details about the error, if any.
	 * 
	 * @return Additional details about the error.
	 */
	public String getErrorDetails() {
		return errorDetails;
	}

	/**
	 * Indicates whether the message is about an error or not.
	 * 
	 * @return {@code true} is the message is about an error, {@code false} otherwise.
	 */
	public boolean isError() {
		return isError;
	}

}
