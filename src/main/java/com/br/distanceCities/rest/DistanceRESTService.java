package com.br.distanceCities.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.br.distanceCities.model.City;

@Path("/distance")
public class DistanceRESTService {

	@GET
    @Path("/{returnType:([xX][mM][lL])|([jJ][sS][oO][nN])}/{measureUnit:([kK][mM])|([mM][iI])}/{id:[0-9][0-9]*}/{id2:[0-9][0-9]*}")
    public Response getTwoParamPath(@PathParam("id") Integer id,@PathParam("id2") Integer id2,
    		@PathParam("returnType") String returnType, @PathParam("measureUnit") String measureUnit){

		System.out.println("returnType="+returnType);
		System.out.println("unit="+measureUnit);
    	System.out.println("id1="+id);
    	System.out.println("id2="+id2);
    	return Response.status(Response.Status.NOT_IMPLEMENTED).build();
    }
	
    @GET
    @Path("/{id:[0-9][0-9]*}/{id2:[0-9][0-9]*}")
    public Response getTwoParam(@PathParam("id") Integer id,@PathParam("id2") Integer id2,
    		@QueryParam("returnType") String returnType, @QueryParam("measureUnit") String measureUnit){
    	validateQueryParam(returnType,measureUnit);
    	
    	System.out.println("id1="+id);
    	System.out.println("id2="+id2);
    	return Response.status(Response.Status.NOT_IMPLEMENTED).build();
    }
    
    private void validateQueryParam(String returnType, String measureUnit) {
    	if (returnType == null) {
			throw new WebApplicationException(
					Response.status(Response.Status.BAD_REQUEST).entity("returnType parameter is mandatory").build());
		}else if(returnType.isEmpty() || !("xml".equals(returnType) || "json".equals(returnType))){
			throw new WebApplicationException(
					Response.status(Response.Status.BAD_REQUEST).entity("returnType parameter is invalid, only 'xml' or 'json' are available").build());
		
		}
    	
    	if (measureUnit == null) {
			throw new WebApplicationException(
					Response.status(Response.Status.BAD_REQUEST).entity("measureUnit parameter is mandatory").build());
		}else if(measureUnit.isEmpty() || !("km".equals(measureUnit) || "mi".equals(measureUnit))){
			throw new WebApplicationException(
					Response.status(Response.Status.BAD_REQUEST).entity("measureUnit parameter is invalid, only 'km' or 'mi' are available").build());
		
		}
	}

	@GET
    @Path("/{type:[JX]}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response example(@PathParam("type") String type) {
    	City c = new City();
    	c.setId(99999);
    	c.setName("Sampa");
    	String mediaType = type.equalsIgnoreCase("J")?MediaType.APPLICATION_JSON:MediaType.APPLICATION_XML;
    	
    	return Response.ok(c, mediaType).build();
    }
}
