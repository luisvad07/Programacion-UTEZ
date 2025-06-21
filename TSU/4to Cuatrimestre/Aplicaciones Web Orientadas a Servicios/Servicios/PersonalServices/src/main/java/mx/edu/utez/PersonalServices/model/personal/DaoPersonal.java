package mx.edu.utez.PersonalServices.model.personal;

import mx.edu.utez.PersonalServices.model.Repository;
import mx.edu.utez.PersonalServices.model.position.BeanPosition;
import mx.edu.utez.PersonalServices.utils.MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoPersonal implements Repository<BeanPersonal> {

    Connection conn;
    PreparedStatement ps;
    ResultSet rs;
    MySQLConnection mysql = new MySQLConnection();

    @Override
    public List<BeanPersonal> findAll() {
        List<BeanPersonal> personal = new ArrayList<>();
        BeanPersonal person = null;
        BeanPosition position = null;
        try {
            conn = mysql.getConnection();
            String query = "SELECT pe.*, po.description FROM personal pe" +
                    "JOIN position po ON pe.position_id = po.id;";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()){
                person = new BeanPersonal();
                position = new BeanPosition();
                person.setId(rs.getLong("id"));
                person.setName(rs.getString("name"));
                person.setSurname(rs.getString("surname"));
                person.setLastname(rs.getString("lastname"));
                person.setBirthday(rs.getString("birthday"));
                person.setSalary(rs.getDouble("salary"));
                position.setDescription(rs.getString("description"));
                person.setPosition(position);
                personal.add(person);
            }
        } catch (SQLException e){
            Logger.getLogger(DaoPersonal.class.getName())
                    .log(Level.SEVERE, "Error -> findAll " + e.getMessage());
        } finally {
            mysql.close(conn,ps,rs);
        }
        return personal;
    }

    @Override
    public BeanPersonal findOne(Long id) {
        return null;
    }

    @Override
    public boolean save(BeanPersonal object) {
        return false;
    }

    @Override
    public boolean update(BeanPersonal object) {
        return false;
    }

    @Override
    public boolean delete(BeanPersonal id) {
        return false;
    }
}
