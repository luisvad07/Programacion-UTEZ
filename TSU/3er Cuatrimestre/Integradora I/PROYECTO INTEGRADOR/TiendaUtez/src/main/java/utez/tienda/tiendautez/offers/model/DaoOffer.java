package utez.tienda.tiendautez.offers.model;

import utez.tienda.tiendautez.utils.MySQLConnection;


import java.sql.*;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoOffer {

    Connection conn;
    PreparedStatement pstm;
    ResultSet rs;

    private final String GET_OFFERS = "SELECT * FROM offers;";
    private final String GET_OFFERS_CHEKC = "SELECT * FROM offers WHERE status =  0";
    private final  String INSERT_OFFER = "INSERT INTO offers (name, discount, start_date, end_date, banner) VALUES (?, ?, ?, ?, ?);";
    private final String  UPDATE_OFFER = "UPDATE offers  SET name = ?, discount = ?, start_date = ?, end_date = ?, banner=?  WHERE id_offers = ?;";
    private final String FIND_OFFER = "SELECT * FROM offers WHERE id_offers = ?;";
    private final String  A = "select * from offers where   CURRENT_DATE() between start_date and end_date";
    private final String DELETE_OFFER = "DELETE FROM offers WHERE id_offers = ?;";

    //--------- -------------FIND OFERRS------------------------------

    public BeanOffer findOffer(Long id_offers){
        BeanOffer offer = new BeanOffer(0L, "", 0, null, null, "");
        try {
            conn = new MySQLConnection().getConnection();
            String query = FIND_OFFER;
            pstm = conn.prepareStatement(query);
            pstm.setLong(1,id_offers);
            rs = pstm.executeQuery();

            if (rs.next()){
                offer.setId_offers(rs.getLong("id_offers"));
                offer.setName(rs.getString("name"));
                offer.setDiscount(rs.getInt("discount"));
                offer.setStart_date(rs.getDate("start_date"));
                offer.setEnd_date(rs.getDate("end_date"));
                offer.setStatus(rs.getInt("status"));
                byte[] image = rs.getBytes("banner");
                String imageStr = Base64.getEncoder().encodeToString(image);
                offer.setBanner(imageStr);
            }

        }catch (SQLException e){
            //Logger.getLogger(DaoAdmin.class.getName()).log(Level.SEVERE, "Error en findOffer -> ", e);
        }finally {
          //  closeConnections();
            closeConnections();

        }
        //System.out.println(offer.toString());
        return offer;
    }




    //------------------------------------------------------------------

    public List<BeanOffer> showOffers (){
        List<BeanOffer> offerList = new LinkedList<>();
        BeanOffer offer = null;

        try {
            conn = new MySQLConnection().getConnection();
            String query = GET_OFFERS;
            pstm = conn.prepareStatement(query);
            rs = pstm.executeQuery();
            while (rs.next()){
                offer = new BeanOffer();
                offer.setId_offers(rs.getLong("id_offers"));
                offer.setName(rs.getString("name"));
                offer.setDiscount(rs.getInt("discount"));
                offer.setStart_date(rs.getDate("start_date"));
                offer.setEnd_date(rs.getDate("end_date"));
                offer.setStatus(rs.getInt("status"));

                byte[] image = rs.getBytes("banner");
                String imageStr = Base64.getEncoder().encodeToString(image);

                offer.setBanner(imageStr);



                offerList.add(offer);
            }
        }catch (SQLException e){
            Logger.getLogger(DaoOffer.class.getName()).log(Level.SEVERE, "Error en showOffers -> ", e);
        }finally {
            closeConnections();
        }

        return offerList;
    }

    public boolean saveOffer(BeanOffer offer){
        try {
            conn = new MySQLConnection().getConnection();
            String query = INSERT_OFFER;
            pstm = conn.prepareStatement(query);
            pstm.setString(1, offer.getName());
            pstm.setInt(2, offer.getDiscount());
            pstm.setDate(3, new Date(offer.getStart_date().getTime()));
            pstm.setDate(4, new Date(offer.getEnd_date().getTime()));
            pstm.setString(5,offer.getBanner());
            return pstm.executeUpdate()==1;
        }catch (SQLException e){
          //  Logger.getLogger(DaoAdmin.class.getName()).log(Level.SEVERE, "Error en saveOffer -> ", e);
            return false;
        }finally {
            closeConnections();
        }
    }


    public boolean updateOffer(BeanOffer offer){
        try {
            conn = new MySQLConnection().getConnection();
            String query = UPDATE_OFFER;
            pstm = conn.prepareStatement(query);
            pstm.setString(1, offer.getName());
            pstm.setInt(2, offer.getDiscount());
            pstm.setDate(3, new Date(offer.getStart_date().getTime()));
            pstm.setDate(4, new Date(offer.getEnd_date().getTime()));
            pstm.setString(5,offer.getBanner());
            pstm.setLong(6,offer.getId_offers());

            return pstm.executeUpdate()==1;

        }catch (SQLException e){
            //Logger.getLogger(DaoAdmin.class.getName()).log(Level.SEVERE, "Error en updateOffer -> ", e);
            return false;
        }finally {
            closeConnections();
        }
    }
    public boolean deleteOffer(Long id_offers){
        try {
            conn = new MySQLConnection().getConnection();
            String query = DELETE_OFFER;
            pstm = conn.prepareStatement(query);
            pstm.setLong(1, id_offers);
            return pstm.executeUpdate()==1; //1==1
        }catch (SQLException e){
            //Logger.getLogger(DaoAdmin.class.getName()).log(Level.SEVERE, "Error deleteOffer -> ", e);
            return false;
        } finally {
            closeConnections();
        }
    }

    public void closeConnections(){
        try {
            if (conn!=null){
                conn.close();
            }
            if (pstm!=null){
                pstm.close();
            }
            if (rs!=null){
                rs.close();
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }


    public List<BeanOffer> showOffersCheck (){
        List<BeanOffer> offerList = new LinkedList<>();
        BeanOffer offer = null;

        try {
            conn = new MySQLConnection().getConnection();
            String query = A;
            pstm = conn.prepareStatement(query);
            rs = pstm.executeQuery();
            while (rs.next()){
                offer = new BeanOffer();
                offer.setId_offers(rs.getLong("id_offers"));
                offer.setName(rs.getString("name"));
                offer.setDiscount(rs.getInt("discount"));
                offer.setStart_date(rs.getDate("start_date"));
                offer.setEnd_date(rs.getDate("end_date"));
                offer.setStatus(rs.getInt("status"));

                byte[] image = rs.getBytes("banner");
                String imageStr = Base64.getEncoder().encodeToString(image);

                offer.setBanner(imageStr);



                offerList.add(offer);
            }
        }catch (SQLException e){
            Logger.getLogger(DaoOffer.class.getName()).log(Level.SEVERE, "Error en showOffers -> ", e);
        }finally {
            closeConnections();
        }

        return offerList;
    }



    public static void main(String[] args) {
        new DaoOffer().findOffer(0L);
    }
}
