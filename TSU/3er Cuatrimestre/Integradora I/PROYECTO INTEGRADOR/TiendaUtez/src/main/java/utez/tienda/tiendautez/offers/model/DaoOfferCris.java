package utez.tienda.tiendautez.offers.model;

import utez.tienda.tiendautez.products.normal.model.ProductUserBean;
import utez.tienda.tiendautez.products.normal.model.ProductUserDao;
import utez.tienda.tiendautez.service.ServiceOffer;
import utez.tienda.tiendautez.utils.MySQLConnection;

import java.io.InputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoOfferCris {
    Connection conn;
    PreparedStatement pstm;
    ResultSet rs;

    private final String GET_OFFERS = "SELECT * FROM offers;";
    private final  String INSERT_OFFER = "INSERT INTO offers (name, discount, start_date, end_date, banner) VALUES (?, ?, ?, ?, ?);";
    private final String  UPDATE_OFFER = "UPDATE offers  SET name = ?, discount = ?, start_date = ?, end_date = ?  WHERE id_offers = ?;";
    private final String  UPDATE_BANNER = "UPDATE offers  SET banner=?  WHERE id_offers = ?;";
    private final String  UPDATE_STATUS = "UPDATE offers  SET status=?  WHERE id_offers = ?;";
    private final String FIND_OFFER = "SELECT * FROM offers WHERE id_offers = ?;";
    private final String DELETE_OFFER = "DELETE FROM offers WHERE id_offers = ?;";

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
                offer.setEnd_date2(String.valueOf(rs.getDate("end_date")));
                offer.setStart_date2(String.valueOf(rs.getDate("start_date")));
                byte[] banner = rs.getBytes("banner");
                String bannerStr = Base64.getEncoder().encodeToString(banner);
                offer.setBanner(bannerStr);
                offerList.add(offer);
            }
        }catch (SQLException e){
            Logger.getLogger(DaoOffer.class.getName()).log(Level.SEVERE, "Error en showOffers -> ", e);
        }finally {
            closeConnections();
        }

        return offerList;
    }

    public boolean saveOffer(BeanOffer offer, InputStream banner){
        try {
            conn = new MySQLConnection().getConnection();
            String query = INSERT_OFFER;
            pstm = conn.prepareStatement(query);
            pstm.setString(1, offer.getName());
            pstm.setInt(2, offer.getDiscount());
            pstm.setDate(3, new Date(offer.getStart_date().getTime()));
            pstm.setDate(4, new Date(offer.getEnd_date().getTime()));
            pstm.setBlob(5,banner);
            //pstm.setString(5,offer.getBanner());
            return pstm.executeUpdate()==1;
        }catch (SQLException e){
            Logger.getLogger(DaoOffer.class.getName()).log(Level.SEVERE, "Error en saveOffer -> ", e);
            return false;
        }finally {
            closeConnections();
        }
    }

    public BeanOffer findOffer(Long id_offers){
        try {
            conn = new MySQLConnection().getConnection();
            String query = FIND_OFFER;
            pstm = conn.prepareStatement(query);
            pstm.setLong(1,id_offers);
            rs = pstm.executeQuery();
            while (rs.next()){
                BeanOffer offer = new BeanOffer();
                offer.setId_offers(rs.getLong("id_offers"));
                offer.setName(rs.getString("name"));
                offer.setDiscount(rs.getInt("discount"));
                offer.setStart_date(rs.getDate("start_date"));
                offer.setEnd_date(rs.getDate("end_date"));
                byte[] banner = rs.getBytes("banner");
                String bannerStr = Base64.getEncoder().encodeToString(banner);
                offer.setBanner(bannerStr);
                //offer.setBanner(rs.getString("banner"));
                //admin.setIdRole(rs.getLong("idRole"));
                //admin.setNameRole(rs.getString("nameRole"));
                return offer;
            }
        }catch (SQLException e){
            Logger.getLogger(DaoOffer.class.getName()).log(Level.SEVERE, "Error en findOffer -> ", e);
        }finally {
            closeConnections();
        }

        return null;
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
            //pstm.setString(5,offer.getBanner());
            pstm.setLong(5,offer.getId_offers());

            return pstm.executeUpdate()==1;

        }catch (SQLException e){
            Logger.getLogger(DaoOffer.class.getName()).log(Level.SEVERE, "Error en updateOffer -> ", e);
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
            Logger.getLogger(DaoOffer.class.getName())
                    .log(Level.SEVERE, "Error deleteOffer -> ", e);
            return false;
        } finally {
            closeConnections();
        }
    }

    public boolean updateBanner(BeanOffer offer, InputStream banner){
        try {
            conn = new MySQLConnection().getConnection();
            String query = UPDATE_BANNER;
            pstm = conn.prepareStatement(query);
            pstm.setBlob(1, banner);
            pstm.setLong(2, offer.getId_offers());

            return pstm.executeUpdate()==1;

        }catch (SQLException e){
            Logger.getLogger(DaoOffer.class.getName()).log(Level.SEVERE, "Error en updateBanner -> ", e);
            return false;
        }finally {
            closeConnections();
        }
    }


    public boolean updateStatus(long id, int status){
        try {
            conn = new MySQLConnection().getConnection();
            String query = UPDATE_STATUS;
            pstm = conn.prepareStatement(query);
            pstm.setInt(1,status);
            pstm.setLong(2, id);

            return pstm.executeUpdate()==1;

        }catch (SQLException e){
            Logger.getLogger(DaoOffer.class.getName()).log(Level.SEVERE, "Error en updateOffer -> ", e);
            return false;
        }finally {
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

    public void checkDateOffer(){
        ServiceOffer serviceOffer = new ServiceOffer();
        DaoOfferCris daoOfferCris = new DaoOfferCris();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date fechaActual = new java.util.Date();

        DaoOffer daoOffer = new DaoOffer();


        List<BeanOffer>  offers = daoOffer.showOffers();
        List<BeanOffer> ofertasActives = daoOffer.showOffersCheck();


        for (BeanOffer offer: offers) {
            System.out.println("Generaaaaaal"+offer.getId_offers());

            for (BeanOffer offerActive: ofertasActives) {
                System.out.println("Ofertaaaaaaa"+offerActive.getId_offers());


                if (offer.getId_offers() == offerActive.getId_offers()){
                    System.out.println("Activar a -> oferta" + offer.getId_offers());

                    daoOfferCris.updateStatus(offer.getId_offers(),1);
                    break;
                }else {

                    daoOfferCris.updateStatus(offer.getId_offers(),0);

                    System.out.println("Desactivar a -> oferta" + offer.getId_offers());

                }

            }
            System.out.println("------------------------------------------");

        }



    }

    public static void main(String[] args) {
            new DaoOfferCris().checkDateOffer();
    }
}
