package utez.tienda.tiendautez.products.normal.model;

import utez.tienda.tiendautez.administrator.model.DaoAdmin;
import utez.tienda.tiendautez.offers.model.DaoOffer;
import utez.tienda.tiendautez.products.gestion.model.ProductBean;
import utez.tienda.tiendautez.products.normal.controller.ProductsUserServlet;
import utez.tienda.tiendautez.utils.MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.SortedMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductUserDao {




    Connection conn;
    PreparedStatement pstm;
    ResultSet rs;

    //-----------------------------lIST pRODUCTS ------------------------------------------------------
    private final String QUERY_ALL_PRODUCTS = "select p.delete, p.id_products , p.name, p.description, p.image, p.offers_id_offers, cp.price from products_combination as cp inner join products as p on products_id_products = id_products where  (p.status = 1 and p.delete = 0)  and (cp.size = 'ch' or cp.size = 'NA')";

    private  final String QUERY_TOOFFERS = "select p.name,p.id_products ,  p.description, p.image, p.offers_id_offers, cp.price, cp.products_id_products, p.delete, cp.color, p.offers_id_offers from products_combination as cp inner join products as p on products_id_products = id_products where  (p.status = 1 and p.delete = 0)  and (cp.size = 'ch' or cp.size = 'NA') and  offers_id_offers = ? ";

    private  final String OFFERS_RELATIONS_WITH_PRODUCTS = "SELECT * FROM PRODUCTS WHERE offers_id_offers = ?";

    private final String QUERY_SEARCGING = "select p.id_products , p.name, p.description, p.image, p.offers_id_offers, cp.price, cp.products_id_products, p.delete, cp.color, c.name from products_combination as cp inner join products as p on products_id_products = id_products inner join categories as c on category_id_category=id_category where  (p.status = 1 and p.delete = 0)  and (cp.size = 'ch' or cp.size = 'NA') and p.name like ? or cp.color like ? or c.name like ?";

    private final String QUERY_UPDATE_STATUS = "UPDATE products SET offers_id_offers = ? WHERE id_products = ?";


    public boolean updateStatusProduct(long id){
        // por si borramos con fk int fk_offe
        try {
            conn = new MySQLConnection().getConnection();
            String query = QUERY_UPDATE_STATUS;
            pstm = conn.prepareStatement(query);
            pstm.setInt(1,0);

            pstm.setLong(2, id);

            return pstm.executeUpdate()==1;

        }catch (SQLException e){
            //Logger.getLogger(DaoOffer.class.getName()).log(Level.SEVERE, "Error en updateOffer -> ", e);
            System.out.println(e);
            return false;
        }finally {
            closeConnections();
        }
    }


    public List<ProductUserBean> showProductsPublic(){
        List<ProductUserBean> listProducts =  new ArrayList<>();
        try {
            conn = new MySQLConnection().getConnection();

            pstm = conn.prepareStatement(QUERY_ALL_PRODUCTS);
            rs = pstm.executeQuery();
            int id = 0;
            while (rs.next()){
                if (id != rs.getInt("p.id_products")){
                    ProductUserBean product = new ProductUserBean();
                    product.setId_products(rs.getInt("p.id_products"));
                    product.setName(rs.getString("p.name"));
                    product.setDescription(rs.getString("p.description"));
                    product.setOffers_id_offers(rs.getInt("p.offers_id_offers"));
                    Long id_offer = rs.getLong("offers_id_offers");
                    product.setOffer(new DaoOffer().findOffer(id_offer));
                    //--------------ALmacenar imagen -------------------------------------------
                    byte[] image = rs.getBytes("image");
                    String imageStr = Base64.getEncoder().encodeToString(image);
                    product.setImageToShow(imageStr);
                    //-------------------------------------------------------------------
                    product.setPrice(rs.getDouble("cp.price"));
                    listProducts.add(product);
                    id = rs.getInt("p.id_products");
                    //System.out.println(id);
                }
            }
        }catch (SQLException e){
            Logger.getLogger(DaoAdmin.class.getName()).log(Level.SEVERE, "Error en findCategory -> ", e);
        }finally {
            closeConnections();
        }
       // System.out.println(listProducts.size());

        return listProducts;
    }






    public List<ProductUserBean> banners_objects(int id){
        List<ProductUserBean> listProducts =  new ArrayList<>();
        try {
            conn = new MySQLConnection().getConnection();

            pstm = conn.prepareStatement(QUERY_TOOFFERS);
            pstm.setInt(1,id);
            rs = pstm.executeQuery();
            while (rs.next()){
                if (id != rs.getInt("p.id_products")){
                    ProductUserBean product = new ProductUserBean();
                    product.setId_products(rs.getInt("p.id_products"));
                    product.setName(rs.getString("p.name"));
                    product.setDescription(rs.getString("p.description"));
                    product.setOffers_id_offers(rs.getInt("p.offers_id_offers"));
                    Long id_offer = rs.getLong("offers_id_offers");
                    product.setOffer(new DaoOffer().findOffer(id_offer));
                    //--------------ALmacenar imagen -------------------------------------------
                    byte[] image = rs.getBytes("image");
                    String imageStr = Base64.getEncoder().encodeToString(image);
                    product.setImageToShow(imageStr);
                    //-------------------------------------------------------------------
                    product.setPrice(rs.getDouble("cp.price"));
                    listProducts.add(product);
                    id = rs.getInt("p.id_products");
                    //System.out.println(id);
                }
            }
        }catch (SQLException e){
            Logger.getLogger(DaoAdmin.class.getName()).log(Level.SEVERE, "Error en findCategory -> ", e);
        }finally {
            closeConnections();
        }
        // System.out.println(listProducts.size());

        return listProducts;
    }





    public List<ProductUserBean> searching(String search){
        List<ProductUserBean> listProducts =  new ArrayList<>();
        try {
            conn = new MySQLConnection().getConnection();

            pstm = conn.prepareStatement(QUERY_SEARCGING);
            pstm.setString(1,"%"+search+"%");
            pstm.setString(2,"%"+search+"%");
            pstm.setString(3,"%"+search+"%");
            rs = pstm.executeQuery();
            int id = 0;
            while (rs.next()){
                if (id != rs.getInt("p.id_products")){
                    ProductUserBean product = new ProductUserBean();
                    product.setId_products(rs.getInt("p.id_products"));
                    product.setName(rs.getString("p.name"));
                    product.setDescription(rs.getString("p.description"));
                    product.setOffers_id_offers(rs.getInt("p.offers_id_offers"));
                    Long id_offer = rs.getLong("offers_id_offers");
                    product.setOffer(new DaoOffer().findOffer(id_offer));
                    //--------------ALmacenar imagen -------------------------------------------
                    byte[] image = rs.getBytes("image");
                    String imageStr = Base64.getEncoder().encodeToString(image);
                    product.setImageToShow(imageStr);
                    //-------------------------------------------------------------------
                    product.setPrice(rs.getDouble("cp.price"));
                    listProducts.add(product);
                    id = rs.getInt("p.id_products");
                    //System.out.println(id);
                }
            }
        }catch (SQLException e){
            Logger.getLogger(DaoAdmin.class.getName()).log(Level.SEVERE, "Error en findCategory -> ", e);
        }finally {
            closeConnections();
        }
        // System.out.println(listProducts.size());
        for (ProductUserBean p: listProducts) {
            System.out.println(p.toString());
        }
        return listProducts;
    }
//----------------------------------------------------------------------------------------
public List<ProductUserBean> status_objets(int id){
    List<ProductUserBean> listProducts =  new ArrayList<>();
    try {
        conn = new MySQLConnection().getConnection();

        pstm = conn.prepareStatement(OFFERS_RELATIONS_WITH_PRODUCTS);
        pstm.setInt(1,id);
        rs = pstm.executeQuery();
        while (rs.next()){
                ProductUserBean product = new ProductUserBean();
                product.setId_products(rs.getInt("id_products"));
                listProducts.add(product);

        }
    }catch (SQLException e){
        Logger.getLogger(DaoAdmin.class.getName()).log(Level.SEVERE, "Error en findCategory -> ", e);
    }finally {
        closeConnections();
    }
     System.out.println(listProducts.size());

    return listProducts;
}

    //Cerrar conexiones

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


    public static void main(String[] args) {
        //new ProductUserDao().showProductsPublic();

        new ProductUserDao().status_objets(2);
    }




}
