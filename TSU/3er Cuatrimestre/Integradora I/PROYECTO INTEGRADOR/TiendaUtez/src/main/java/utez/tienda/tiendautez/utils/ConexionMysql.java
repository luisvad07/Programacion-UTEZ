package utez.tienda.tiendautez.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionMysql {

    Connection con=null;

   // final String DBNAME= "utezshop", USERNAME = "root", PASSWORD = "Abejercito22?",   TIMEZONE="America/Mexico_City", USEESL = "false", PUBLICKEY = "true";
    final String DBNAME= "utezshop", USERNAME = "root", PASSWORD = "root",   TIMEZONE="America/Mexico_City", USEESL = "false", PUBLICKEY = "true";


    public Connection getConexion(){
        String dataSource = String.format("jdbc:mysql://localhost/%s?user=%s&password=%s&serverTimezone=%s&useSSL=%s&allowPublicKeyRetrieval=%s",
                DBNAME,USERNAME,PASSWORD,TIMEZONE,USEESL,PUBLICKEY);
        //%s es remplazable por una varibale , sesible a la posicion

        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver()); //Libreria de meaven
            return   con = DriverManager.getConnection(dataSource); //Ejecutamos la consulta (Permiso)
        }catch (SQLException e){
            System.out.println(this.getClass().getCanonicalName()+ "->" + e.getMessage());
        }
        return con ;
    }

//------------PROBAR LOCALMENTE  En consola de java
    public static void main(String[] args) {

        Connection conn = new ConexionMysql().getConexion();
        if (conn != null){
            try {
                System.out.println("Conexion");
            }catch (Exception e){
                System.out.println(e);
            }
        }else {
            System.out.println("No jalooo");
        }
    }

    /*public void closeConnection (Connection conn,PreparedStatement pstm,ResultSet rs) throws SQLException {
        if (conn != null || pstm !=null || rs!= null) {
            conn.close();
            pstm.close();
            rs.close();
        }
    }*/

}
