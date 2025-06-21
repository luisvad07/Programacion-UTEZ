package modelo;

import config.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//Nos ayudara a tener cintacto con la base de datos y extraer datos del objeto
public class AdministradorDAO {
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public Administrador validar(String correo, String pass){
        Administrador admin = new Administrador();
        String sql="SELECT * FROM admin WHERE correo=? and contra=?";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1,correo);
            ps.setString(2,pass);
            rs = ps.executeQuery();

            //Ingresamos a la clase los datos

           while (rs.next()){
               admin.setCorreo(rs.getString("correo"));
               admin.setPassword(rs.getString("contra"));
               admin.setNombre(rs.getString("nombre"));
           }


        }catch (Exception e){
            System.out.println(e);
        }

        return admin;
    }

    public static void main(String[] args) {
        AdministradorDAO dao = new AdministradorDAO();
        Administrador admin = dao.validar("j@gmail.com","123");
        System.out.println(    "Correo: " +  admin.getCorreo()      );
        System.out.println(    "Nombre: " +  admin.getNombre()      );
        System.out.println(    "Password: " +  admin.getPassword()  );


    }
}
