package com.keyin.client;

import javax.xml.transform.Result;
import java.sql.*;

public class DatabaseConnection {
//Implement client here
    private static final String url = "jdbc:postgresql://localhost/APJavaFinalSprint";
    private static final String username = "postgres";
    private static final String password = "9336539421";

    public static Connection getCon(){
        Connection connection = null;
        try {
          Class.forName("org.postgresql.Driver");
          connection = DriverManager.getConnection(url, username, password);
        }  catch (ClassNotFoundException | SQLException e) {
          e.printStackTrace();
        }
        return connection;
    };

}
