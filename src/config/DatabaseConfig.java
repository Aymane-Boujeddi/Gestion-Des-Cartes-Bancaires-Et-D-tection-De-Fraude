package config;

import java.sql.*;

public  class DatabaseConfig {
    private static String url = "jdbc:mysql://127.0.0.1:3306/gestion_carte_bancaire";
    private static String root = "root";
    private static String password = "097680";

   public static Connection getConnection() throws SQLException {
       return DriverManager.getConnection(url,root,password);
   }

}
