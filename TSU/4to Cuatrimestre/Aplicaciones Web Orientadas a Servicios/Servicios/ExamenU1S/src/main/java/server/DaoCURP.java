package server;

import utils.MySQLConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoCURP {
    static PreparedStatement pstm;
    static Connection conn; //Conexion a la BD
    static ResultSet rs; //Cacha registros de la BD


    //Metodo para obtener los registros
    public static List<BeanCURP> registros() {
        Statement stm= null;

        String sql="SELECT * FROM registros";

        List<BeanCURP> list= new ArrayList<>();

        try {
            conn = new MySQLConnection().getConnection();
            pstm = conn.prepareStatement(sql);
            rs=stm.executeQuery(sql);
            while (rs.next()) {
                BeanCURP c=new BeanCURP();
                c.setId(rs.getInt(1));
                c.setNombre(rs.getString(2));
                c.setApellido_Pat(rs.getString(3));
                c.setApellido_Mat(rs.getString(4));
                c.setSexo(rs.getString(5));
                c.setEstado_Nac(rs.getString(6));
                c.setFecha_Nac(rs.getDate(7));
                c.setCURP(rs.getString(8));
                list.add(c);
            }
            stm.close();
            rs.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error!");
            e.printStackTrace();
        }
        return list;
    }

    public static boolean ingresar(BeanCURP person){
        String sql="INSERT INTO person (Nombre, Apellido_Pat, Sexo, Estado_Nac, Fecha_Nac, CURP)" +
                "VALUES (?, ?, ?, ?, ?,?)";

        try {
            conn = new MySQLConnection().getConnection();
            String query = sql;
            pstm = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, person.getNombre());
            pstm.setString(2, person.getApellido_Pat());
            pstm.setString(3, person.getApellido_Mat());
            pstm.setString(4, person.getSexo());
            pstm.setString(5, person.getEstado_Nac());
            pstm.setDate(6, new Date(person.getFecha_Nac().getTime()));
            pstm.setString(8, person.getCURP());
            if (pstm.executeUpdate()==1){ //1==1
                ResultSet lastIdPerson = pstm.getGeneratedKeys();
                if (lastIdPerson.next()){
                    System.out.println(lastIdPerson.getLong(1));
                }else{
                    return false;
                }
            }else {
                return false;
            }
        }catch (SQLException e){
            System.out.println("Error!");
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return true;
    }

    public static BeanCURP findPerson(String CURP){
        String sql = "SELECT * FROM registros WHERE CURP = ?";
        try{
            conn = new MySQLConnection().getConnection();
            String query = sql;
            pstm = conn.prepareStatement(query);
            pstm.setString(1, CURP);
            rs = pstm.executeQuery();
            while (rs.next()){
                BeanCURP c=new BeanCURP();
                c.setId(rs.getInt(1));
                c.setNombre(rs.getString(2));
                c.setApellido_Pat(rs.getString(3));
                c.setApellido_Mat(rs.getString(4));
                c.setSexo(rs.getString(5));
                c.setEstado_Nac(rs.getString(6));
                c.setFecha_Nac(rs.getDate(7));
                c.setCURP(rs.getString(8));
                return c;
            }
        }catch (SQLException e){
            System.out.println("Error!");
            e.printStackTrace();
        }finally {
            closeConnections();
        }
        return null;
    }

    //Metodo para cerrar Conexiones
    public static void closeConnections(){
        try{
            if (conn!= null){
                conn.close();
            }
            if (pstm!= null){
                pstm.close();
            }
            if (rs!= null){
                rs.close();
            }
        }catch (SQLException e){
            System.out.println(e);
        }
    }

}
