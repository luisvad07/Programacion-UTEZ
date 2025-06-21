package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {

    //Empieza las declaraciones para conectar a la BD
    public Connection getConnection() {
        final String DBNAME = "CURP",
                USERNAME = "root",
                PASSWORD = "root",
                TIMEZONE = "America/Mexico_City",
                USESSL = "false",
                PUBLICKEY = "true";
        String dataSource = String.format("jdbc:mysql://localhost:3306/%s?user=%s" +
                        "&password=%s&serverTimezone=%s&useSSL=%s&allowPublicKeyRetrieval=%s",
                DBNAME, USERNAME, PASSWORD, TIMEZONE, USESSL, PUBLICKEY);
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            return DriverManager.getConnection(dataSource);
        } catch (SQLException e) {
            System.out.println(this.getClass().getCanonicalName() +
                    "->" + e.getMessage());
        }
        return null;
    }

    //Prueba de Conexion con la BD a traves de la consola
    public static void main(String[] args) {
        Connection conn = new MySQLConnection().getConnection();
        if(conn != null) {
            try {
                System.out.println("Conectado a la BD :)");
                conn.close();
            }catch (SQLException e){
                System.out.println();
            }
        }else{
            System.out.println("No pusiste conectarte a la BD! :(");
        }
    }
}
