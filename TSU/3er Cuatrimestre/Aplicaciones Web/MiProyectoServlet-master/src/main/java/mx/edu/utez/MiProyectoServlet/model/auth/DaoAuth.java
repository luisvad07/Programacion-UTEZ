package mx.edu.utez.MiProyectoServlet.model.auth;

import mx.edu.utez.MiProyectoServlet.utils.MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoAuth {
    Connection conn;
    PreparedStatement pstm;
    ResultSet rs;

    private final String GET_LOGIN = "SELECT * FROM person JOIN user " +
            "ON person.id = user.person WHERE username = ? AND password = ?";

    public  BeanAuth login(String username, String password){
        BeanAuth login = new BeanAuth();
        try{
            conn = new MySQLConnection().getConnection();
            String query = GET_LOGIN;
            pstm = conn.prepareStatement(query);
            pstm.setString(1,username);
            pstm.setString(2, password);
            rs = pstm.executeQuery();
            if(rs.next()) {
                login.setIdP(rs.getLong("person.id"));
                login.setIdU(rs.getLong("user.id"));
                String fullname = rs.getString("name") + " " + rs.getString("lastname");
                login.setFulname(fullname);
                login.setEmail(rs.getString("email"));
                login.setUsername(rs.getString("username"));
                login.setRole(rs.getString("role"));
                byte[] image = rs.getBytes("image");
                String imageStr = Base64.getEncoder().encodeToString(image);
                login.setImage(imageStr);

                return login;
            }else{
                return null;
            }
        }catch (SQLException e){
            Logger.getLogger(DaoAuth.class.getName())
                    .log(Level.SEVERE, "Error en DaoAuth.login-> "+e);
        return null;
        }finally {
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
