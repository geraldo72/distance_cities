package com.br.distanceCities.db.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.br.distanceCities.db.CrudDAO;
import com.br.distanceCities.db.JdbcDAO;
import com.br.distanceCities.exception.DatabaseException;
import com.br.distanceCities.model.City;

public class CityDAO extends JdbcDAO implements CrudDAO<City>{

	@Override
	public City insert(City request) throws DatabaseException{
		String sql = "INSERT INTO java.City (name,latitude,longitude) VALUES (?, ?, ?)";
		
		
		PreparedStatement statement = null;
		Connection connection = null;
		
		try{
			connection = getConnection();
			statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		
			statement.setString(1, request.getName());
			statement.setBigDecimal(2, request.getLatitude());
			statement.setBigDecimal(3, request.getLongitude());
			 
			int affectedRows = statement.executeUpdate();
			
			if (affectedRows < 1) {
	            throw new DatabaseException("Creating city failed, no rows affected.");
	        }
			
			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                request.setId(generatedKeys.getInt(1));
	            }
	            else {
	                throw new DatabaseException("Creating city failed, no ID obtained.");
	            }
	        }
			return request;
			
		} catch (SQLException e) {
			throw new DatabaseException("Não foi possivel incluir a cidade", e);
		}finally {
			closeConnections(connection, statement);
		}
		
	}

	@Override
	public void delete(City request) throws DatabaseException {
		String sql = "DELETE FROM java.City WHERE id=?";

		PreparedStatement statement = null;
		Connection connection = null;
		
		try{
			connection = getConnection();
			statement = connection.prepareStatement(sql);
			statement.setInt(1, request.getId());
		
			int affectedRows = statement.executeUpdate();
			if(affectedRows < 1){
				throw new DatabaseException("Nenhum registro foi deletado.");
			}
			
		} catch (SQLException e) {
			throw new DatabaseException("Não foi possivel excluir a cidade", e);
		}finally {
			closeConnections(connection, statement);
		}
		
	}

	@Override
	public void update(City request) throws DatabaseException {
		String sql = "UPDATE java.City SET name=?, latitude=?, longitude=? WHERE id=?";

		PreparedStatement statement = null;
		Connection connection = null;
		
		try{
			connection = getConnection();
			statement = connection.prepareStatement(sql);
		
			statement.setString(1, request.getName());
			statement.setBigDecimal(2, request.getLatitude());
			statement.setBigDecimal(3, request.getLongitude());
			
			statement.setInt(4, request.getId());
			 
			int affectedRows = statement.executeUpdate();
			if(affectedRows < 1){
				throw new DatabaseException("Nenhum registro foi atualizado.");
			}
			
		} catch (SQLException e) {
			throw new DatabaseException("Não foi possivel atualizar a cidade", e);
		}finally {
			closeConnections(connection, statement);
		}
		
	}

	@Override
	public List<City> listAll() throws DatabaseException{
		List<City> resultados = new ArrayList<City>();
		String sql = "SELECT id,name,latitude,longitude FROM java.city";
		
		ResultSet rs = null;
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = getConnection();
			statement = connection.prepareStatement(sql);
			rs = statement.executeQuery();
			while (rs.next()) {
				City temp = new City();
				temp.setId(rs.getInt("id"));
				temp.setName(rs.getString("name"));
				temp.setLatitude(rs.getBigDecimal("latitude"));
				temp.setLongitude(rs.getBigDecimal("longitude"));
				resultados.add(temp);
			}
			return resultados;
		} catch (SQLException e) {
			throw new DatabaseException("Erro ao buscar todas cidades", e);
		} finally {
			closeConnections(connection, statement,rs);
		}
	}

	@Override
	public List<City> findBy(City request) throws DatabaseException {
		List<City> resultados = new ArrayList<City>();
		String sql = "SELECT id,name,latitude,longitude FROM java.city where "
				+ "(? IS NULL or id = ?) and "
				+ "(? IS NULL or name = ?) and "
				+ "(? IS NULL or latitude = ?) and "
				+ "(? IS NULL or longitude = ?)";
		
		ResultSet rs = null;
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = getConnection();
			statement = connection.prepareStatement(sql);

			statement.setInt(1, request.getId());
			statement.setInt(2, request.getId());
			
			statement.setString(3, request.getName());
			statement.setString(4, request.getName());

			statement.setBigDecimal(5, request.getLatitude());
			statement.setBigDecimal(6, request.getLatitude());
			
			statement.setBigDecimal(7, request.getLongitude());
			statement.setBigDecimal(8, request.getLongitude());
			 
			rs = statement.executeQuery();
			while (rs.next()) {
				City temp = new City();
				temp.setId(rs.getInt("id"));
				temp.setName(rs.getString("name"));
				temp.setLatitude(rs.getBigDecimal("latitude"));
				temp.setLongitude(rs.getBigDecimal("longitude"));
				resultados.add(temp);
			}
			return resultados;
		} catch (SQLException e) {
			throw new DatabaseException("Erro ao buscar todas cidades", e);
		} finally {
			closeConnections(connection, statement,rs);
		}
	}
	
	@Override
	public City findFirst(City request) throws DatabaseException{
		List<City> resultado = findBy(request);
		if(!resultado.isEmpty()){
			return resultado.get(0);
		}
		return null;
	}

}
