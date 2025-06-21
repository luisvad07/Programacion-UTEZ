package mx.edu.utez.personalservice.model.personal;

import mx.edu.utez.personalservice.model.Repository;
import mx.edu.utez.personalservice.model.position.BeanPosition;
import mx.edu.utez.personalservice.utils.MySQL;
import mx.edu.utez.personalservice.utils.Response;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoPersonal implements Repository<BeanPersonal> {
    Connection conn;
    PreparedStatement ps;
    CallableStatement cs;
    ResultSet rs;
    MySQL mysql = new MySQL();
    Response response = new Response();

    @Override
    public List<BeanPersonal> findAll() {
        List<BeanPersonal> personal = new ArrayList<>();
        BeanPersonal person = null;
        BeanPosition position = null;

        try {
            conn = mysql.getConnection();
            String query = "SELECT pe.*, po.id as idp, po.position, po.description FROM personal pe " +
                    "JOIN positions po ON pe.position_id = po.id";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                person =  new BeanPersonal();
                position = new BeanPosition();
                person.setId(rs.getLong("id"));
                person.setName(rs.getString("name"));
                person.setSurname(rs.getString("surname"));
                person.setLastname(rs.getString("lastname"));
                person.setBirthday(rs.getString("birthday"));
                person.setSalary(rs.getDouble("salary"));
                position.setId(rs.getLong("idp"));
                position.setPosition(rs.getString("position"));
                position.setDescription(rs.getString("description"));
                person.setPosition(position);
                personal.add(person);

            }
        } catch (SQLException e) {
            Logger.getLogger(DaoPersonal.class.getName()).log(Level.SEVERE, "Error -> findAll" + e.getMessage());
        } finally {
            mysql.close(conn, ps, rs);
        }
        return personal;
    }

    @Override
    public Response<BeanPersonal> findById(Long id) {
        BeanPersonal person = null;
        BeanPosition position = null;

        try {
            conn = mysql.getConnection();
            String query = "SELECT pe.*, po.id as idp, po.position, po.description FROM personal pe " +
                    " JOIN positions po ON pe.position_id = po.id where pe.id = ?";
            ps = conn.prepareStatement(query);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            if (rs.next()){
                person =  new BeanPersonal();
                position = new BeanPosition();
                person.setId(rs.getLong("id"));
                person.setName(rs.getString("name"));
                person.setSurname(rs.getString("surname"));
                person.setLastname(rs.getString("lastname"));
                person.setBirthday(rs.getString("birthday"));
                person.setSalary(rs.getDouble("salary"));
                position.setId(rs.getLong("idp"));
                position.setPosition(rs.getString("position"));
                position.setDescription(rs.getString("description"));
                person.setPosition(position);

                response.setError(false);
                response.setStatus(200);
                response.setMessage("Ok");
                response.setData(person);
            } else {
                response.setError(true);
                response.setStatus(400);
                response.setMessage("No se pudo insertar");
                response.setData(null);
            }
        } catch (SQLException e) {
            Logger.getLogger(DaoPersonal.class.getName()).log(Level.SEVERE, "Error -> findById" + e.getMessage());
            response.setError(true);
            response.setStatus(400);
            response.setMessage("Error -> " + e.getMessage());
            response.setData(null);
        } finally {
            mysql.close(conn, ps, rs);
        }
        return response;
    }

    @Override
    public Response<BeanPersonal> save(BeanPersonal person) {
        try {
            conn = mysql.getConnection();
            String query = "INSERT INTO personal (name, surname, lastname, salary, position_id, birthday) " +
                    "VALUES (?, ?, ?, ?, ?, ?);\n";
            ps = conn.prepareStatement(query);
            ps.setString(1,person.getName());
            ps.setString(2,person.getSurname());
            ps.setString(3,person.getLastname());
            ps.setDouble(4,person.getSalary());
            ps.setLong(5,person.getPosition().getId());
            ps.setString(6,person.getBirthday());

            if (ps.executeUpdate() == 1){
                response.setError(false);
                response.setStatus(200);
                response.setMessage("Ok");
                response.setData(person);
            } else {
                response.setError(true);
                response.setStatus(400);
                response.setMessage("Row was not found");
                response.setData(null);
            }
        } catch (SQLException e) {
            Logger.getLogger(DaoPersonal.class.getName()).log(Level.SEVERE, "Error -> save" + e.getMessage());
            response.setError(true);
            response.setStatus(400);
            response.setMessage("Error -> " + e.getMessage());
            response.setData(null);
        } finally {
            mysql.close(conn, ps, rs);
        }
        return response;
    }

    @Override
    public Response<BeanPersonal> update(BeanPersonal person) {
        try {
            conn = mysql.getConnection();
            String query = "UPDATE personal SET name = ?, surname = ?," +
                    "lastname = ?, salary = ?, position_id = ?, birthday = ? WHERE personal.id = ?;";
            ps = conn.prepareStatement(query);
            ps.setString(1,person.getName());
            ps.setString(2,person.getSurname());
            ps.setString(3,person.getLastname());
            ps.setDouble(4,person.getSalary());
            ps.setLong(5,person.getPosition().getId());
            ps.setString(6,person.getBirthday());
            ps.setLong(7, person.getId());

            if (ps.executeUpdate() == 1){
                response.setError(false);
                response.setStatus(200);
                response.setMessage("Ok");
                response.setData(person);
            } else {
                response.setError(true);
                response.setStatus(400);
                response.setMessage("Nothing Found");
                response.setData(null);
            }
        } catch (SQLException e) {
            Logger.getLogger(DaoPersonal.class.getName()).log(Level.SEVERE, "Error -> update" + e.getMessage());
            response.setError(true);
            response.setStatus(400);
            response.setMessage("Error -> " + e.getMessage());
            response.setData(null);
        } finally {
            mysql.close(conn, ps, rs);
        }
        return response;
    }

    @Override
    public Response<BeanPersonal> delete(Long id) {
        try {
            conn = mysql.getConnection();
            String query = "DELETE FROM personal WHERE personal.id = ?";
            ps = conn.prepareStatement(query);
            ps.setLong(1,id);

            if (ps.executeUpdate() == 1){
                response.setError(false);
                response.setStatus(200);
                response.setMessage("Ok");
                response.setData(id);
            } else {
                response.setError(true);
                response.setStatus(400);
                response.setMessage("Row was not found");
                response.setData(null);
            }
        } catch (SQLException e) {
            Logger.getLogger(DaoPersonal.class.getName()).log(Level.SEVERE, "Error -> delete" + e.getMessage());
            response.setError(true);
            response.setStatus(400);
            response.setMessage("Error -> " + e.getMessage());
            response.setData(null);
        } finally {
            mysql.close(conn, ps, rs);
        }
        return response;
    }
}
