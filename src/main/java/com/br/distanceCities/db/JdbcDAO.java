package com.br.distanceCities.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.br.distanceCities.exception.DatabaseException;

public abstract class JdbcDAO {

	private final String URL = "jdbc:mysql://localhost:3306";
	private final String USER = "root";
	private final String PASS = "teste";

	protected final Connection getConnection() throws DatabaseException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection(URL, USER, PASS);
			
		} catch (ClassNotFoundException e) {
			throw new DatabaseException("Erro ao carregar o driver", e);
		} catch (SQLException e) {
			throw new DatabaseException("Erro ao conectar", e);
		}
	}
	
	protected void closeConnections( Connection connection, PreparedStatement statement) throws DatabaseException {
		
		closeConnections(connection, statement, null);
		
	}
	
	protected void closeConnections(Connection connection, PreparedStatement statement, ResultSet resultSet) throws DatabaseException {
		try {
			if(connection != null){
				connection.close();
			}
			if(statement != null){
				statement.close();
			}
			if(resultSet != null){
				resultSet.close();
			}
		} catch (SQLException e) {
			throw new DatabaseException("Não foi possivel fechar as conexoes", e);
		}
	}

}