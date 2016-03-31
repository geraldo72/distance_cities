package com.br.distanceCities.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.br.distanceCities.exception.DatabaseException;

public abstract class JdbcDAO {

	private final String URL = "mysql.url";
	private final String USER = "mysql.user";
	private final String PASS = "mysql.pass";

	protected final Connection getConnection() throws DatabaseException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			ClassLoader classloader = Thread.currentThread().getContextClassLoader();
			Properties prop = new Properties();
			prop.load(classloader.getResourceAsStream("connection.properties"));
			
			return DriverManager.getConnection(
					prop.getProperty(URL, "jdbc:mysql://localhost:3306"), 
					prop.getProperty(USER, "teste"),
					prop.getProperty(PASS, "teste"));
			
		} catch (ClassNotFoundException e) {
			throw new DatabaseException("Erro ao carregar o driver", e);
		} catch (SQLException e) {
			throw new DatabaseException("Erro ao conectar", e);
		} catch (IOException e) {
			throw new DatabaseException("Erro ao carregar arquivo de properties", e);
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