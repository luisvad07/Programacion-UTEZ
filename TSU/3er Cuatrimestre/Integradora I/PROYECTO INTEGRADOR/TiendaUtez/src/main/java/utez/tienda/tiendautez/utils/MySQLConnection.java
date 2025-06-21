package utez.tienda.tiendautez.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {


    //metodo para hacer la conexion
    public Connection getConnection() {
        final String DBNAME = "utezshop",
                USERNAME = "root",
                PASSWORD = "root",
                //PASSWORD = "Abejercito22?",
                TIMEZONE = "America/Mexico_City",
                USESSL = "false",
                PUBLICKEY = "true";

        //esto es para poder pasar paremetros
        String dataSource = String.format("jdbc:mysql://localhost:3306/%s?user=%s" +
                        "&password=%s&serverTimezone=%s&useSSL=%s&allowPublicKeyRetrieval=%s",
                DBNAME,USERNAME,PASSWORD,TIMEZONE,USESSL,PUBLICKEY);

        try{
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            return DriverManager.getConnection(dataSource);
            //aqui se manda la peticion completa a traves de la variable
        }catch (SQLException e){
            System.out.println(this.getClass().getCanonicalName() + "->" + e.getMessage());
            //aqui te dice que fue lo que ocurrio o que trono
        }
        return null;//aqui es en caso de que no suceda la conexion por eso se pone
    }
    //fin metodo para hacer la conexion
// ------------------------------------------------------------------------
    //esto es para probar la conexion
    public static void main(String[] args) {

        Connection conn = new MySQLConnection().getConnection();
        //aqui se manda a ejecutar la cenexion y nuetro metodo "getConnection()"

        if(conn != null) {
            try {
                System.out.println("Conexion realizada :)"); // se avisa que la conexion fue exitosa
                conn.close();
                //aqui se cierra la conexion eso es importanta para evitar ataques y cosas similares
            }catch (SQLException e){
                System.out.println(e);
            }
        }else{
            System.out.println("Conexion fallida :(");
        }
    }
    //aqui finaliza el metodo para probar la conexion
}
