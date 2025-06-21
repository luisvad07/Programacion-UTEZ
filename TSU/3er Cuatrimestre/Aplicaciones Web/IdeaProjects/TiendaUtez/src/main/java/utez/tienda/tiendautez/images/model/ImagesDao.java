package utez.tienda.tiendautez.images.model;

import utez.tienda.tiendautez.administrator.model.DaoAdmin;
import utez.tienda.tiendautez.images.model.ImagesBean;
import utez.tienda.tiendautez.products.gestion.model.ProductBean;
import utez.tienda.tiendautez.utils.MySQLConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ImagesDao {

    Connection conn;
    PreparedStatement pstm;
    ResultSet rs;

    //-----------------------------FIND ONE CATRGORY ------------------------------------------------------
    private final String QUERY_FIND_IMAGES = "SELECT * FROM images WHERE products_id_products = ?  ";

    public List<ImagesBean> findImages(int products_id_products){
        List<ImagesBean> listImages = new ArrayList<>();

        try {
            conn = new MySQLConnection().getConnection();

            pstm = conn.prepareStatement(QUERY_FIND_IMAGES);
            pstm.setInt(1,products_id_products);
            rs = pstm.executeQuery();

            while (rs.next()){

                byte[] image = rs.getBytes("image");
                String imageStr = Base64.getEncoder().encodeToString(image);

                ImagesBean images = new ImagesBean(rs.getInt("id_image"),imageStr,rs.getInt("products_id_products") );
                listImages.add(images);

            }
        }catch (SQLException e){
            Logger.getLogger(DaoAdmin.class.getName()).log(Level.SEVERE, "Error en findCategory -> ", e);
        }finally {
            closeConnections();
        }


        /*for (ImagesBean images : listImages) {
            System.out.println(images.toString());
        }*/

        return listImages;
    }

    //--------------------------------------------------------------------------------------------------------
    private final String QUERY_INSERT_IMAGES = "INSERT INTO `images`(`image`,`products_id_products`)VALUES(?,?)";

    public boolean addImages(ImagesBean images){
        boolean result = false;
        try {
            conn = new MySQLConnection().getConnection();
            pstm = conn.prepareStatement(QUERY_INSERT_IMAGES);
            pstm.setBlob(1,images.getInputImages());
            pstm.setInt(2,images.getProducts_id_products());
            result = pstm.executeUpdate()== 1 ;
        }catch (SQLException e){
            System.out.println(e);
            // Logger.getLogger(DaoAdmin.class.getName()).log(Level.SEVERE, "Error en findCategory -> ", e);
        }finally {
            closeConnections();
        }
       // System.out.println("---------------------"+result);
        return result;
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

    //-----------------------------------------TEST---------------------------------


    public static void main(String[] args) {
        new  ImagesDao().findImages(12);
    }

}
