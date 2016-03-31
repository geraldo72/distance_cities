package com.br.distanceCities.rest.util;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.br.distanceCities.exception.ApplicationException;
import com.br.distanceCities.model.Error;

@Provider
public class ApplicationMapper implements ExceptionMapper<ApplicationException>  {
	
	@Override 
    public Response toResponse(ApplicationException exception) { 
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
    		   		  .entity(new Error(exception))
    		   		  .type(MediaType.APPLICATION_JSON)
    		   		  .build(); 
    } 

}
