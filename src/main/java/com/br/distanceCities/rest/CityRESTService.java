package com.br.distanceCities.rest;

import java.util.List;

import javax.ws.rs.GET;
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
    @Path("/{type:[JX]}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response lookupMemberById(@PathParam("type") String type) {
    	City c = new City();
    	c.setId(99999);
    	c.setName("Sampa");
    	String mediaType = type.equalsIgnoreCase("J")?MediaType.APPLICATION_JSON:MediaType.APPLICATION_XML;
    	
    	return Response.ok(c, mediaType).build();
    }

}
