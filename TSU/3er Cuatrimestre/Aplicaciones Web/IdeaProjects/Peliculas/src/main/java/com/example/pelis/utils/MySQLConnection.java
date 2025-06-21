package mx.edu.utez.Peliculas.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {
    public Connection getConnection(){
        final String DBNAME = "movies",
                USERNAME = "root",
                PASSWORD = "",
                TIMEZONE = "America/Mexico_City",
                USESSL = "false",
                PUBLICKEY = "true";

        String dataSource = String.format("jdbc:mysql://localhost:3306/%s?user=%s" + //192.168.62.185
                "&password=%s&serverTimezone=%s&useSSL=%s&allowPublicKeyRetrieval=%s",
                DBNAME, USERNAME, PASSWORD, TIMEZONE, USESSL, PUBLICKEY);

        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            return DriverManager.getConnection(dataSource);
        }catch (SQLException e){
            System.out.println(this.getClass().getCanonicalName() + " -> " + e.getMessage());
        }
        return null;
    }

    public static void main(String[] args) {
        Connection conn = new MySQLConnection().getConnection();
        if (conn != null) {
            try {
                System.out.println("Conexión realizada :)");
                conn.close();
            }catch (SQLException e){
                System.out.println(e);
            }
        }else{
            System.out.println("Conexión fallida :(");
        }
    }
}
