package mx.edu.utez.MiProyectoServlet.model.person;

import mx.edu.utez.MiProyectoServlet.utils.MySQLConnection;

import java.io.InputStream;
import java.sql.*;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoPerson {
    Connection conn;
    PreparedStatement pstm;
    ResultSet rs;

    private final String GET_PERSONS = "SELECT * FROM person JOIN user ON person.id=user.person";
    private final String INSERT_PERSON = "INSERT INTO person (name, lastname, age, email, phone, birthday, image)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";
    private final String INSERT_USER = "INSERT INTO user (username, password, role, person)" +
            "VALUES (?, ?, ?, ?)";
    private final String FIND_PERSON = "SELECT * FROM person WHERE id = ?";
    private final String UPDATE_PERSON = "UPDATE person SET name = ?, lastname = ?, age = ?, email = ?, phone = ?" +
            "WHERE id = ?";
    private final String DELETE_PERSON = "DELETE FROM person WHERE id = ?";

    public List<BeanPerson> showPersons (){
        List<BeanPerson> personList = new LinkedList<>();
        BeanPerson person = null;
        try{
            conn = new MySQLConnection().getConnection();
            String query = GET_PERSONS;
            pstm = conn.prepareStatement(query);
            rs = pstm.executeQuery();
            while (rs.next()){
                person = new BeanPerson();
                person.setId(rs.getLong("person.id"));
                person.setName(rs.getString("name"));
                person.setLastname(rs.getString("lastname"));
                person.setAge(rs.getInt("age"));
                person.setEmail(rs.getString("email"));
                person.setPhone(rs.getString("phone"));

                person.setBirthday(rs.getDate("birthday"));
                byte[] image = rs.getBytes("image");
                String imageStr = Base64.getEncoder().encodeToString(image);
                person.setImage(imageStr);

                person.setUsername(rs.getString("username"));
                person.setPassword(rs.getString("password"));
                person.setRole(rs.getString("role"));
                person.setIdU(rs.getLong("user.id"));

                personList.add(person);
            }
        }catch (SQLException e){
            Logger.getLogger(DaoPerson.class.getName())
                    .log(Level.SEVERE, "Error en showPersons -> ", e);
        }finally {
            closeConnections();
        }
        return personList;
    }

    public boolean savePerson(BeanPerson person, InputStream image){
        try {
            conn = new MySQLConnection().getConnection();
            String query = INSERT_PERSON;
            pstm = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, person.getName());
            pstm.setString(2, person.getLastname());
            pstm.setInt(3, person.getAge());
            pstm.setString(4, person.getEmail());
            pstm.setString(5, person.getPhone());
            pstm.setDate(6, new Date(person.getBirthday().getTime()));
            pstm.setBlob(7, image);
            if (pstm.executeUpdate()==1){ //1==1
                ResultSet lastIdPerson = pstm.getGeneratedKeys();
                if (lastIdPerson.next()){
                    System.out.println(lastIdPerson.getLong(1));
                    return saveUser(person.getUsername(), person.getPassword(), person.getRole(), lastIdPerson.getLong(1));
                }else{
                    return false;
                }
            }else {
                return false;
            }
        }catch (SQLException e){
            Logger.getLogger(DaoPerson.class.getName())
                    .log(Level.SEVERE, "Error savePerson -> ", e);
            return false;
        } finally {
            closeConnections();
        }
    }

    public boolean saveUser(String username, String password, String role, Long person){
        try {
            String query = INSERT_USER;
            pstm = conn.prepareStatement(query);
            pstm.setString(1, username);
            pstm.setString(2, password);
            pstm.setString(3, role);
            pstm.setLong(4, person);

            return pstm.executeUpdate()==1;
        }catch (SQLException e){
            Logger.getLogger(DaoPerson.class.getName())
                    .log(Level.SEVERE, "Error saveUser -> ", e);
            return false;
        }
    }

    public BeanPerson findPerson (Long id){
        try{
            conn = new MySQLConnection().getConnection();
            String query = FIND_PERSON;
            pstm = conn.prepareStatement(query);
            pstm.setLong(1, id);
            rs = pstm.executeQuery();
            while (rs.next()){
                BeanPerson person = new BeanPerson();
                person.setId(rs.getLong("id"));
                person.setName(rs.getString("name"));
                person.setLastname(rs.getString("lastname"));
                person.setAge(rs.getInt("age"));
                person.setEmail(rs.getString("email"));
                person.setPhone(rs.getString("phone"));
                return person;
            }
        }catch (SQLException e){
            Logger.getLogger(DaoPerson.class.getName())
                    .log(Level.SEVERE, "Error en findPerson -> ", e);
        }finally {
            closeConnections();
        }
        return null;
    }

    public boolean updatePerson(BeanPerson person){
        try {
            conn = new MySQLConnection().getConnection();
            String query = UPDATE_PERSON;
            pstm = conn.prepareStatement(query);
            pstm.setString(1, person.getName());
            pstm.setString(2, person.getLastname());
            pstm.setInt(3, person.getAge());
            pstm.setString(4, person.getEmail());
            pstm.setString(5, person.getPhone());
            pstm.setLong(6, person.getId());
            return pstm.executeUpdate()==1; //1==1
        }catch (SQLException e){
            Logger.getLogger(DaoPerson.class.getName())
                    .log(Level.SEVERE, "Error updatePerson -> ", e);
            return false;
        } finally {
            closeConnections();
        }
    }

    public boolean deletePerson(Long id){
        try {
            conn = new MySQLConnection().getConnection();
            String query = DELETE_PERSON;
            pstm = conn.prepareStatement(query);
            pstm.setLong(1, id);
            return pstm.executeUpdate()==1; //1==1
        }catch (SQLException e){
            Logger.getLogger(DaoPerson.class.getName())
                    .log(Level.SEVERE, "Error deletePerson -> ", e);
            return false;
        } finally {
            closeConnections();
        }
    }

    public void closeConnections(){
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
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
