package config;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {
    Connection con; //Variable para la conexion de datos dada por mysql-conect (dependencia)
    String url = "jdbc:mysql://localhost/utez"; //Url de la base de datos

    String user = "root"; //Usuario

    String pass= ""; //Contraseña de la base de datos

    public Connection getConexion(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver"); //Buscamos el driver
            con = DriverManager.getConnection(url,user,pass); //Hacemos la conexion
        }catch (Exception e){
            System.out.println("Fallo"+e);//Imprimir fallo

        }
        return con;
    }
//----------------------Validamos si funciona mediante consola de java ------------------------
    public static void main(String[] args) {
        Conexion conex = new Conexion();
        Connection conexx = conex.getConexion();
        if (conexx != null){
            System.out.println("Entro a la base de datos");
        }else {
            System.out.println("NO entro :((");
        }
    }
}
