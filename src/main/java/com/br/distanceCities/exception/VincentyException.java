package com.br.distanceCities.exception;

public class VincentyException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5335216551208372447L;

	/**
	 * @param message
	 * @param e
	 */
	public VincentyException(final String message, final Throwable e) {
		super(message,e);
	}
	
	/**
	 * @param e
	 */
	public VincentyException(final Throwable e) {
		super(e);
	}
	
	/**
	 * @param message
	 */
	public VincentyException(final String message) {
		super(message);
	}
}
