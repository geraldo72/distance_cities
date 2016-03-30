package com.br.distanceCities.exception;

public class DatabaseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5335216551208372447L;

	/**
	 * @param message
	 * @param e
	 */
	public DatabaseException(final String message, final Throwable e) {
		super(message,e);
	}
	
	/**
	 * @param e
	 */
	public DatabaseException(final Throwable e) {
		super(e);
	}
	
	/**
	 * @param message
	 */
	public DatabaseException(final String message) {
		super(message);
	}
}
