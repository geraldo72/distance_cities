package com.br.distanceCities.exception;

public class ApplicationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5335216551208372447L;

	/**
	 * @param message
	 * @param e
	 */
	public ApplicationException(final String message, final Throwable e) {
		super(message,e);
	}
	
	/**
	 * @param e
	 */
	public ApplicationException(final Throwable e) {
		super(e);
	}
	
	/**
	 * @param message
	 */
	public ApplicationException(final String message) {
		super(message);
	}
}
