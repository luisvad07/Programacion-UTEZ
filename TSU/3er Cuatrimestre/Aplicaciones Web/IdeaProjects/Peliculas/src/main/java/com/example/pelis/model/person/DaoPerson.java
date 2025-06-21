package mx.edu.utez.Peliculas.model.person;

import mx.edu.utez.Peliculas.utils.MySQLConnection;

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

    private final String GET_PERSONS = "SELECT * FROM movies";
    private final String INSERT_PERSON = "INSERT INTO movies (name, description, publish_date, actors, duration, ranking, image)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";
    private final String FIND_PERSON = "SELECT * FROM movies WHERE id = ?";
    private final String UPDATE_PERSON = "UPDATE movies SET name = ?, description= ?, actors = ?, duration = ?, ranking = ?" +
            "WHERE id = ?";
    private final String DELETE_PERSON = "DELETE FROM movies WHERE id = ?";

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
                person.setId(rs.getLong("id"));
                person.setName(rs.getString("name"));
                person.setDescription(rs.getString("description"));
                person.setPublish_date(rs.getDate("publish_date"));
                person.setActors(rs.getString("actors"));
                person.setDuration(rs.getInt("duration"));
                person.setRanking(rs.getDouble("ranking"));
                byte[] image = rs.getBytes("image");
                String imageStr = Base64.getEncoder().encodeToString(image);
                person.setImage(imageStr);

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
            pstm.setString(2, person.getDescription());
            pstm.setDate(3, new Date(person.getPublish_date().getTime()));
            pstm.setString(4, person.getActors());
            pstm.setInt(5, person.getDuration());
            pstm.setDouble(6, person.getRanking());
            pstm.setBlob(7, image);
        }catch (SQLException e){
            Logger.getLogger(DaoPerson.class.getName())
                    .log(Level.SEVERE, "Error savePerson -> ", e);
            return false;
        } finally {
            closeConnections();
        }
        return false;
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
                person.setDescription(rs.getString("description"));
                person.setActors(rs.getString("actors"));
                person.setDuration(rs.getInt("duration"));
                person.setRanking(rs.getDouble("ranking"));
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
            pstm.setString(2, person.getDescription());
            pstm.setString(3, person.getActors());
            pstm.setInt(4, person.getDuration());
            pstm.setDouble(5, person.getRanking());
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