package com.br.distanceCities.rest;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.br.distanceCities.model.City;

@Path("/cities")
@RequestScoped
public class CityRESTService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<City> listAllCities() {
    	List<City> retorno = new ArrayList<>();
    	City c = new City();
    	c.setId(99999l);
    	c.setName("Sampa");
    	retorno.add(c);
    	c = new City();
    	c.setId(88888l);
    	c.setName("rio");
    	retorno.add(c);
    	c = new City();
    	c.setId(77777l);
    	c.setName("floripa");
    	retorno.add(c);
        return retorno;
    }

    @GET
    @Path("/{type:[JX]}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response lookupMemberById(@PathParam("type") String type) {
    	City c = new City();
    	c.setId(99999l);
    	c.setName("Sampa");
    	String mediaType = type.equalsIgnoreCase("J")?MediaType.APPLICATION_JSON:MediaType.APPLICATION_XML;
    	
    	return Response.ok(c, mediaType).build();
    }

}
