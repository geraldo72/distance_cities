package com.br.distanceCities.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Error {
	
	private String name;
	private String message;
	private Throwable cause;
	
	public Error(){
		super();
	}
	
	public Error(String name, String message, Throwable cause){
		super();
		this.name = name;
		this.message = message;
		this.cause = cause;
	}
	
	public Error(Exception exception){
		super();
		this.name = exception.getClass().getName();
		this.message = exception.getMessage();
		this.cause = exception.getCause();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Throwable getCause() {
		return cause;
	}

	public void setCause(Throwable cause) {
		this.cause = cause;
	}

	
}
