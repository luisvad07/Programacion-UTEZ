package History;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.MySQLConnection;

public class DaoHistory {
    Connection conn;
    PreparedStatement pstm;
    ResultSet rs;

    private final String INSERT_OPERATION = "insert into operation(type,first_number,second_number,result) "
            + "values(?,?,?,?)";
    private final String GET_OPERATIONS = "select * from operation";
    
    public boolean saveOperation(String type, double first_number, double second_number, double result){
        try{
            conn = new MySQLConnection().getConnection();
            String query = INSERT_OPERATION;
            pstm = conn.prepareStatement(query);
            pstm.setString(1, type);
            pstm.setDouble(2, first_number);
            pstm.setDouble(3, second_number);
            pstm.setDouble(4, result);
            
            return pstm.executeUpdate()==1;
        }catch(SQLException e){
            Logger.getLogger(DaoHistory.class.getName())
                    .log(Level.SEVERE, "Error saveOperation ->", e);
            return false;
        }
    }
    
    public ArrayList<BeanHistory> showOperations (){
        ArrayList<BeanHistory> historyList = new ArrayList<>();
        BeanHistory history = null;
        try{
            conn = new MySQLConnection().getConnection();
            String query = GET_OPERATIONS;
            pstm = conn.prepareStatement(query);
            rs = pstm.executeQuery();
            while(rs.next()){
                history = new BeanHistory();
                history.setId(rs.getInt("id"));
                history.setType(rs.getString("type"));
                history.setFirst_number(rs.getDouble("first_number"));
                history.setSecond_number(rs.getDouble("second_number"));
                history.setResult(rs.getDouble("result"));
                history.setCreatedAt(rs.getDate("created_at"));
                
                historyList.add(history);
            }
        }catch(SQLException e){
            Logger.getLogger(DaoHistory.class.getName())
                    .log(Level.SEVERE, "Error en showOperations -> ", e);
        }finally{
            closeConnections();
        }
        return historyList;
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
