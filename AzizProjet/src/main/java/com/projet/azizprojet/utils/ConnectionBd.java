package com.projet.azizprojet.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionBd {
	private static ConnectionBd instance;
	private Connection cnx;

	private final String URL = "jdbc:mysql://localhost:3306/dbpi";
	private final String USER = "root";
	private final String PASSWORD = "";

	private ConnectionBd() {
		try {
			cnx = DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println("Connecting !");
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}

	public static synchronized ConnectionBd getInstance() {
		if(instance == null)
			instance = new ConnectionBd();
		return instance;
	}

	public Connection getCnx() {
		return cnx;
	}
}
