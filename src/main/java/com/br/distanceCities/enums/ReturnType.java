package com.br.distanceCities.enums;

import javax.ws.rs.core.MediaType;

public enum ReturnType {

	XML(MediaType.APPLICATION_XML),
	JSON(MediaType.APPLICATION_JSON);
	
	private String type;
	
	ReturnType(String type){
		this.type = type;
	}
	
	public String type(){
		return type;
	}
	
    public static ReturnType fromName(final String name) {
        for (ReturnType s : ReturnType.values()) {
            if (s.name().equalsIgnoreCase(name)) {
                return s;
            }
        }
        return null;
    }
	
}
