package com.br.distanceCities.exception;

public class DistanceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5335216551208372447L;

	/**
	 * @param message
	 * @param e
	 */
	public DistanceException(final String message, final Throwable e) {
		super(message,e);
	}
	
	/**
	 * @param e
	 */
	public DistanceException(final Throwable e) {
		super(e);
	}
	
	/**
	 * @param message
	 */
	public DistanceException(final String message) {
		super(message);
	}
}
