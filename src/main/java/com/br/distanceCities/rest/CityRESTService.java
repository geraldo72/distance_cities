package com.br.distanceCities.rest;

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

import com.br.distanceCities.db.CrudDAO;
import com.br.distanceCities.db.impl.CityDAO;
import com.br.distanceCities.exception.ApplicationException;
import com.br.distanceCities.exception.DatabaseException;
import com.br.distanceCities.model.City;

@Path("/cities")
public class CityRESTService {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listAllCities() throws ApplicationException {
		try {
			CrudDAO<City> dao = new CityDAO();
			Iterable<City> retorno = dao.listAll();
			return Response.ok(retorno).build();
		} catch (DatabaseException e) {
			throw new ApplicationException(e);
		}
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response lookupMemberById(@PathParam("id") Integer id) throws ApplicationException {
		try {
			CrudDAO<City> dao = new CityDAO();
			City retorno = dao.findFirst(new City(id));
			return Response.ok(retorno).build();
		} catch (DatabaseException e) {
			throw new ApplicationException(e);
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response insertCity(City city) throws ApplicationException {
		try {
			CrudDAO<City> dao = new CityDAO();
			City retorno = dao.insert(city);
			return Response.ok(retorno).build();
		} catch (DatabaseException e) {
			throw new ApplicationException(e);
		}
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response updateCity(@PathParam("id") Integer id, City city) throws ApplicationException {
		try {
			CrudDAO<City> dao = new CityDAO();
			city.setId(id);
			dao.update(city);

			City retorno = dao.findFirst(new City(id));
			return Response.ok(retorno).build();
		} catch (DatabaseException e) {
			throw new ApplicationException(e);
		}
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response deleteCity(@PathParam("id") Integer id) throws ApplicationException {
		try {
			CrudDAO<City> dao = new CityDAO();
			dao.delete(new City(id));
			return Response.ok(id).build();
		} catch (DatabaseException e) {
			throw new ApplicationException(e);
		}
	}

}
