package com.br.distanceCities.rest.util;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.br.distanceCities.model.Error;

@Provider
public class WebApplicationMapper implements ExceptionMapper<WebApplicationException>  {
	
	@Override 
    public Response toResponse(WebApplicationException exception) { 
		
		return Response.status(Response.Status.BAD_REQUEST)
    		   		  .entity(new Error(exception))
    		   		  .build(); 
    } 

}
