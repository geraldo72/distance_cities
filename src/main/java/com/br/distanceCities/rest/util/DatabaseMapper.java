package com.br.distanceCities.rest.util;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.br.distanceCities.exception.DatabaseException;

@Provider
public class DatabaseMapper implements ExceptionMapper<DatabaseException>  {
	
	@Override 
    public Response toResponse(DatabaseException arg0) { 
       return Response.status(Response.Status.BAD_REQUEST)
    		   		  .entity("{\"message\":"+arg0.getMessage()+",\"cause\":"+arg0.getCause()+"}")
    		   		  .build(); 
    } 

}
