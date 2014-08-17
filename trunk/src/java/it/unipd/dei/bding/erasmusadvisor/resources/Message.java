/*
 * Copyright 2014 University of Padua, Italy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package it.unipd.dei.bding.erasmusadvisor.resources;

/**
 * Represents an error message.
 * 
 * @author Nicola Ferro
 * @version 1.00
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
	 * @param code
	 *            the code of the error.
	 * @param message
	 *            the error message.
	 * @param details
	 *            additional details about the error.
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
	 * @param message
	 *            the message.
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
	 * @return the message.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Returns the code of the error, if any.
	 * 
	 * @return the code of the error, if any.
	 */
	public String getErrorCode() {
		return errorCode;
	}
	
	/**
	 * Returns additional details about the error, if any.
	 * 
	 * @return additional details about the error.
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
