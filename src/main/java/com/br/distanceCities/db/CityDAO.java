package com.br.distanceCities.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.br.distanceCities.model.City;

public class CityDAO {

	private final String URL = "jdbc:mysql://localhost:3306";

	private Connection con;
	private Statement comando;

	private void conectar() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(URL, "root", "teste");
			comando = con.createStatement();
			System.out.println("Conectado!");
		} catch (ClassNotFoundException e) {
			// imprimeErro("Erro ao carregar o driver", e.getMessage());
		} catch (SQLException e) {
			// imprimeErro("Erro ao conectar", e.getMessage());
		}
	}

	public List<City> buscarTodos() {
		conectar();
		List<City> resultados = new ArrayList<City>();
		ResultSet rs;
		try {
			rs = comando.executeQuery("SELECT id,name,latitude,longitude FROM java.city");
			while (rs.next()) {
				City temp = new City();
				// pega todos os atributos da pessoa
				temp.setId(rs.getInt("id"));
				temp.setName(rs.getString("name"));
				temp.setLatitude(rs.getBigDecimal("latitude"));
				temp.setLongitude(rs.getBigDecimal("longitude"));
				resultados.add(temp);
			}
			return resultados;
		} catch (SQLException e) {
//			imprimeErro("Erro ao buscar pessoas", e.getMessage());
			System.out.println("erro " + e.getMessage());
			return null;
		} catch (Exception e) {
			System.out.println("erro " + e.getMessage());
			return null;
		}finally {
			fechar();
		}
	}

	private void fechar() {
		try {
			comando.close();
			con.close();
			System.out.println("Conexão Fechada");
		} catch (SQLException e) {
			// imprimeErro("Erro ao fechar conexão", e.getMessage());
		}
	}

}
