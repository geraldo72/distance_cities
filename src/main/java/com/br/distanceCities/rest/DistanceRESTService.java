package com.br.distanceCities.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.br.distanceCities.core.DistanceCalculation;
import com.br.distanceCities.db.CrudDAO;
import com.br.distanceCities.db.impl.CityDAO;
import com.br.distanceCities.enums.ReturnType;
import com.br.distanceCities.enums.Unit;
import com.br.distanceCities.exception.ApplicationException;
import com.br.distanceCities.exception.DatabaseException;
import com.br.distanceCities.exception.DistanceException;
import com.br.distanceCities.model.City;
import com.br.distanceCities.model.Distance;

@Path("/distance")
public class DistanceRESTService {
	
    @GET
    @Path("/{id:[0-9][0-9]*}/{id2:[0-9][0-9]*}")
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Response listOneDistance(@PathParam("id") Integer id,@PathParam("id2") Integer id2,
    		@QueryParam("returnType") String returnType, @QueryParam("measureUnit") String measureUnit) throws ApplicationException {
    	validateQueryParam(returnType,measureUnit);

    	Unit unitEnum = Unit.fromName(measureUnit);
    	ReturnType typeEnum = ReturnType.fromName(returnType);
    	
    	try{
    	
			CrudDAO<City> dao = new CityDAO();
			City city1 = dao.findFirst(new City(id));
			City city2 = dao.findFirst(new City(id2));
			
			DistanceCalculation dc = new DistanceCalculation(unitEnum, city1, city2);
			Distance distance = dc.getDistance();

			return Response.ok(distance,typeEnum.type()).build();
    	
    	}catch(DatabaseException e){
    		throw new ApplicationException(e);
    	} catch (DistanceException e) {
    		throw new ApplicationException(e);
		}
    	
    }
    
    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Response listOneDistance(@PathParam("id") Integer id,
    		@QueryParam("returnType") String returnType, @QueryParam("measureUnit") String measureUnit) throws ApplicationException {
    	validateQueryParam(returnType,measureUnit);
    	
    	Unit unitEnum = Unit.fromName(measureUnit);
    	ReturnType typeEnum = ReturnType.fromName(returnType);
    	
    	try{
	    	CrudDAO<City> dao = new CityDAO();
	    	City city1 = dao.findFirst(new City(id)); 	
	    	
	    	List<City> listCity = dao.listAll();
	    	List<Distance> distances = new ArrayList<Distance>(listCity.size());
	    	for (City city2 : listCity) {
				if(!city2.equals(city1)){//Nao calcula distancia pra ela mesma
					
					DistanceCalculation dc = new DistanceCalculation(unitEnum, city1, city2);
					
					distances.add(dc.getDistance());
					
				}
			}
	    	return Response
	    			.ok(new GenericEntity<List<Distance>>(distances){}      
	    		    ,typeEnum.type())
	    			.build();
    	}catch(DatabaseException e){
    		throw new ApplicationException(e);
    	} catch (DistanceException e) {
    		throw new ApplicationException(e);
		}
    }

	@GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response listAllDistances(@QueryParam("returnType") String returnType, @QueryParam("measureUnit") String measureUnit) throws ApplicationException {
		validateQueryParam(returnType,measureUnit);
    	
    	Unit unitEnum = Unit.fromName(measureUnit);
    	ReturnType typeEnum = ReturnType.fromName(returnType);
    	
    	try{
	    	CrudDAO<City> dao = new CityDAO();
	    	
	    	List<City> listCity = dao.listAll();
	    	List<Distance> distances = generateCombination(unitEnum,listCity);
	    	
	    	return Response
	    			.ok(new GenericEntity<List<Distance>>(distances){}
	    			,typeEnum.type())
	    			.build();
    	}catch(DatabaseException e){
    		throw new ApplicationException(e);
    	} catch (DistanceException e) {
    		throw new ApplicationException(e);
		}
    }
	
	private List<Distance> generateCombination(Unit unitEnum, List<City> cities) throws DistanceException{
		List<Distance> distances = new ArrayList<Distance>();

		//Indice para gerar sublista sem o elemento corrente
		int index = 1;
		
		for (City city1 : cities) {
			
			List<City> comb = cities.subList(index, cities.size());
			for (City city2 : comb) {
				DistanceCalculation dc = new DistanceCalculation(unitEnum, city1, city2);
				distances.add(dc.getDistance());
			}
			index++;	
		}
		return distances;
		
	}

    private void validateQueryParam(String returnType, String measureUnit) throws ApplicationException {
    	if (returnType == null) {
			throw new ApplicationException("returnType parameter is mandatory");
			
		}else if(returnType.isEmpty() || !("xml".equalsIgnoreCase(returnType) || "json".equalsIgnoreCase(returnType))){
			throw new ApplicationException("returnType parameter is invalid, only 'xml' or 'json' are available");
		
		}
    	
    	if (measureUnit == null) {
			throw new ApplicationException("measureUnit parameter is mandatory");
			
		}else if(measureUnit.isEmpty() || !("km".equalsIgnoreCase(measureUnit) || "mi".equalsIgnoreCase(measureUnit))){
			throw new ApplicationException("measureUnit parameter is invalid, only 'km' or 'mi' are available");
		
		}
	}
}
