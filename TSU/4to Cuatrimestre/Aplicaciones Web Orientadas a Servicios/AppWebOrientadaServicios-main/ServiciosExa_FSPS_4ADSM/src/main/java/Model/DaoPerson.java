package Model;

import utils.MySQLConnection;

import java.sql.*;
import java.util.ArrayList;

public class DaoPerson {
    Connection conn;
    PreparedStatement pstm;
    CallableStatement cstm;
    ResultSet rs;

    private String INSERT_PERSON = "insert into persons(nombre,primerApellido,segundoApellido,sexo,estado,fechaNac,curp) values(?,?,?,?,?,?,?)";
    private String GET_PERSON = "select * from persons where curp = ?";

    public boolean saverPerson(String nombre, String primerApellido, String segundoApellido, String sexo, String estado, Date fechaNac, String curp){
        try{
            conn = new MySQLConnection().getConnection();
            String query = INSERT_PERSON;
            pstm = conn.prepareStatement(query);

            pstm.setString(1,nombre);
            pstm.setString(2,primerApellido);
            pstm.setString(3,segundoApellido);
            pstm.setString(4,sexo);
            pstm.setString(5,estado);
            pstm.setDate(6,fechaNac);
            pstm.setString(7,curp);

            return pstm.executeUpdate()==1;
        }catch(SQLException e){

            return false;
        }
    }

    public ArrayList<BeanPerson> showPerson (String curp){
        ArrayList<BeanPerson> personList = new ArrayList<>();
        BeanPerson person = null;
        try{
            conn = new MySQLConnection().getConnection();
            String query = GET_PERSON;
            pstm = conn.prepareStatement(query);
            pstm.setString(1,curp);
            rs = pstm.executeQuery();
            while(rs.next()){
                person = new BeanPerson();
                person.setNombre(rs.getString("nombre"));
                person.setPrimerApellido(rs.getString("primerApellido"));
                person.setSegundoApellido(rs.getString("segundoApellido"));
                person.setSexo(rs.getString("sexo"));
                person.setEstado(rs.getString("estado"));
                person.setFechaNac(rs.getDate("fechaNac"));
                person.setCurp(rs.getString("curp"));

                personList.add(person);
            }
        }catch(SQLException e){

        }finally{
            closeConnections();
        }
        return personList;
    }

    public void  closeConnections(){
        try{
            if(conn != null){
                conn.close();
            }
            if(pstm != null){
                pstm.close();
            }
            if(rs != null){
                rs.close();
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
