package org.pi.demo.utils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {

    //DB PARAM
    static final String URL ="jdbc:mysql://localhost:3306/dbpi";
    static final String USER ="root";
    static final String PASSWORD ="";

    //var
    private Connection cnx;
    //1
    private static volatile MyConnection instance;

    //const
    //2
    public MyConnection() throws SQLException {
        cnx = DriverManager.getConnection(URL, USER, PASSWORD);
        System.out.println("Successful database connection");
    }

    public Connection getCnx() {
        return cnx;
    }

    //3
    public static MyConnection getInstance() throws SQLException {
        if(instance == null) {
            synchronized (MyConnection.class) {
                if(instance == null) {
                    instance = new MyConnection();
                }
            }
        }
        return instance;
    }

    public void closeConnection() throws SQLException {
        if(cnx != null && !cnx.isClosed()) {
            cnx.close();
        }
    }
}