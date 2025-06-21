package server;

import utils.MySQLConnection;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoRPC {
    Connection conn;
    PreparedStatement pstm;
    ResultSet rs;

    private final String HISTORY= "SELECT * FROM operations";
    private final String INSERT = "INSERT INTO person (`type`, first_number, second_number, result, created_at)" +
            "VALUES (?, ?, ?, ?, ?)";

    public List<BeanRPC> showRPC (){
        List<BeanRPC> RPCList = new LinkedList<>();
        BeanRPC RPC = null;
        try{
            conn = new MySQLConnection().getConnection();
            String query = HISTORY;
            pstm = conn.prepareStatement(query);
            rs = pstm.executeQuery();
            while (rs.next()){
                RPC = new BeanRPC();
                RPC.setId(rs.getLong("id"));
                RPC.setType(rs.getString("`type`"));
                RPC.setFirst_number(rs.getDouble("first_number"));
                RPC.setSecond_number(rs.getDouble("second_number"));
                RPC.setResult(rs.getDouble("result"));
                RPC.setCreated_at(rs.getDate("created_at"));
                RPCList.add(RPC);
            }
        }catch (SQLException e){
            Logger.getLogger(DaoRPC.class.getName())
                    .log(Level.SEVERE, "Error en Dao -> ", e);
        }finally {
            closeConnections();
        }
        return RPCList;
    }

    public boolean saveRPC(BeanRPC RPC){
        try {
            conn = new MySQLConnection().getConnection();
            String query = INSERT;
            pstm = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, RPC.getType());
            pstm.setDouble(2, RPC.getFirst_number());
            pstm.setDouble(3, RPC.getSecond_number());
            pstm.setDouble(4, RPC.getResult());
            //pstm.setDate(6, today);
        }catch (SQLException e){
            Logger.getLogger(DaoRPC.class.getName())
                    .log(Level.SEVERE, "Error Insert -> ", e);
        } finally {
            closeConnections();
        }
        return false;
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
            System.out.println();
        }
    }

}



