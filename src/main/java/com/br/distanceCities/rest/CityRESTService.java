package com.br.distanceCities.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.br.distanceCities.db.CityDAO;
import com.br.distanceCities.model.City;

@Path("/cities")
public class CityRESTService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAllCities() {
    	CityDAO dao = new CityDAO();
    	List<City> retorno = dao.buscarTodos();
        return Response.ok(retorno).build();
    }
    
    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response lookupMemberById(@PathParam("id") Integer id) {

    	return Response.status(Response.Status.NOT_IMPLEMENTED).build();
    }
    
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    public Response insertCity(City city){
    	System.out.println(city.getId());
    	System.out.println(city.getName());
    	System.out.println(city.getLatitude());
    	System.out.println(city.getLongitude());
    	return Response.status(Response.Status.NOT_IMPLEMENTED).build();
    }
    
    @PUT
    @Path("/{id:[0-9][0-9]*}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    public Response updateCity(@PathParam("id") Integer id, City city){
    	System.out.println(id);
    	System.out.println(city.getId());
    	System.out.println(city.getName());
    	System.out.println(city.getLatitude());
    	System.out.println(city.getLongitude());
    	return Response.status(Response.Status.NOT_IMPLEMENTED).build();
    }
    
    @DELETE
    @Path("/{id:[0-9][0-9]*}")
    public Response deleteCity(@PathParam("id") Integer id){

    	System.out.println(id);
    	return Response.status(Response.Status.NOT_IMPLEMENTED).build();
    }

    /**
     * EXEMPLOS
     */

    @GET
    @Path("/{id:[0-9][0-9]*}/{id2:[0-9][0-9]*}")
    public Response getTwoParam(@PathParam("id") Integer id,@PathParam("id2") Integer id2){
    	System.out.println("id1="+id);
    	System.out.println("id2="+id2);
    	return Response.status(Response.Status.NOT_IMPLEMENTED).build();
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
