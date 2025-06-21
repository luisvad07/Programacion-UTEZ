package utez.tienda.tiendautez.products.gestion.model;

import utez.tienda.tiendautez.administrator.model.DaoAdmin;
import utez.tienda.tiendautez.category.model.CategoryDao;
import utez.tienda.tiendautez.images.model.ImagesDao;
import utez.tienda.tiendautez.offers.model.DaoOffer;
import utez.tienda.tiendautez.utils.MySQLConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductDao {

    Connection conn;
    PreparedStatement pstm;
    ResultSet rs;

    //-----------------------------lIST pRODUCTS ------------------------------------------------------
    private final String QUERY_ALL_PRODUCTS = "SELECT * FROM products";

    public List<ProductBean> showProducts(){
        List<ProductBean> listProducts =  new ArrayList<>();
        try {
            conn = new MySQLConnection().getConnection();

            pstm = conn.prepareStatement(QUERY_ALL_PRODUCTS);
            rs = pstm.executeQuery();

            while (rs.next()){
                ProductBean product = new ProductBean();
                product.setId_products(rs.getInt("id_products"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setDescriptionLong(rs.getString("description_long"));
                product.setType(rs.getString("type"));
                product.setStatus(rs.getInt("status"));
                product.setOffers_id_offers(rs.getInt("offers_id_offers"));
                //--------------ALmacenar imagen -------------------------------------------
                byte[] image = rs.getBytes("image");
                String imageStr = Base64.getEncoder().encodeToString(image);
                product.setImageToShow(imageStr);
                //-------------------------------------------------------------------
                product.setDelete(rs.getInt("delete"));
                //--------Check ID of catrgory to search info-------------------------
                int id_category = rs.getInt("category_id_category");
                product.setCategory(new CategoryDao().findCategory(id_category)); //Guardamos el objeto de categoria
                //--------Check ID of offers to search info-------------------------
                Long id_offer = rs.getLong("offers_id_offers");
                product.setOffer(new DaoOffer().findOffer(id_offer));
                //-------------------------Check the combinations---------------------
                //Well The id is in the combinatio's table, so i should take its number
                int id_combination = rs.getInt("id_products");
                product.setCombinations(new CombinationPDDao().findCombinations(id_combination));
                //--------------Check the combinatins images-------------------}
                int id_imagesCombinatios = rs.getInt("id_products");
                product.setImagesSecondaries(new ImagesDao().findImages(id_imagesCombinatios));


                listProducts.add(product);

            }
        }catch (SQLException e){
            Logger.getLogger(DaoAdmin.class.getName()).log(Level.SEVERE, "Error en findCategory -> ", e);
        }finally {
            closeConnections();
        }

        /*for (ProductBean p : listProducts) {
            System.out.println(p.toString());
            System.out.println("----------------------------Combinations------------------------------------");
            for (CombinationPDBean cp: p.getCombinations()) {
                System.out.println( cp.toString());
            }
            System.out.println("--------------------------Images Secondaries--------------------");
            for (ImagesBean ip: p.getImagesSecondaries()) {
                System.out.println(ip.toString());
            }
            System.out.println("---------------------"+p.getId_products()+"------------------------");
            System.out.println("\n \n");
        }*/
        return listProducts;
    }

    //--------------------------------------------------------------------------------------------------------

    private final String QUERY_INSERT_PRODUCTS = "INSERT INTO products(name,description,description_long,image,category_id_category)VALUES(?,?,?,?,?)";

    //-------------------------------Insert Product General
    public int addProductGeneral(ProductBean product){
        int id = 0;
        try {
            conn = new MySQLConnection().getConnection();
            pstm = conn.prepareStatement(QUERY_INSERT_PRODUCTS, Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1,product.getName());
            pstm.setString(2,product.getDescription());
            pstm.setString(3,product.getDescriptionLong());
            pstm.setBlob(4,product.getImageToInsert());
            pstm.setInt(5,product.getCategory_id_category());
            if (pstm.executeUpdate()== 1){
                ResultSet lastId = pstm.getGeneratedKeys();
                if (lastId.next()){
                  //  System.out.println(lastId.getInt(1));
                    id=lastId.getInt(1);
                }
            }

        }catch (SQLException e){
            System.out.println(e);
           // Logger.getLogger(DaoAdmin.class.getName()).log(Level.SEVERE, "Error en findCategory -> ", e);
        }finally {
            closeConnections();
        }
        return id;
    }


    //-----------------------------find pRODUCTS ------------------------------------------------------
    private final String QUERY_FIND_PRODUCTS = "SELECT * FROM products WHERE id_products=? ";

    public ProductBean findProducts(int id){
        ProductBean product = new ProductBean();
        try {
            conn = new MySQLConnection().getConnection();

            pstm = conn.prepareStatement(QUERY_FIND_PRODUCTS);
            pstm.setInt(1,id);
            rs = pstm.executeQuery();

            while (rs.next()){
                product = new ProductBean();
                product.setId_products(rs.getInt("id_products"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setDescriptionLong(rs.getString("description_long"));
                product.setType(rs.getString("type"));
                product.setStatus(rs.getInt("status"));
                product.setOffers_id_offers(rs.getInt("offers_id_offers"));
                //--------------ALmacenar imagen -------------------------------------------
                byte[] image = rs.getBytes("image");
                String imageStr = Base64.getEncoder().encodeToString(image);
                product.setImageToShow(imageStr);
                //-------------------------------------------------------------------
                product.setDelete(rs.getInt("delete"));
                //--------Check ID of catrgory to search info-------------------------
                int id_category = rs.getInt("category_id_category");
                product.setCategory(new CategoryDao().findCategory(id_category)); //Guardamos el objeto de categoria
                //--------Check ID of offers to search info-------------------------
                Long id_offer = rs.getLong("offers_id_offers");
                product.setOffer(new DaoOffer().findOffer(id_offer));
                //-------------------------Check the combinations---------------------
                //Well The id is in the combinatio's table, so i should take its number
                int id_combination = rs.getInt("id_products");
                product.setCombinations(new CombinationPDDao().findCombinations(id_combination));
                //--------------Check the combinatins images-------------------}
                int id_imagesCombinatios = rs.getInt("id_products");
                product.setImagesSecondaries(new ImagesDao().findImages(id_imagesCombinatios));

            }
        }catch (SQLException e){
            Logger.getLogger(DaoAdmin.class.getName()).log(Level.SEVERE, "Error en findCategory -> ", e);
        }finally {
            closeConnections();
        }

     //   System.out.println(product.toString());

        return product;
    }


    //-----------------------------------Update status and type --------------------------
    private final String UPDATE_STATUSTYPE= "UPDATE products SET type = ?, status = ? WHERE (id_products = ?)";

    public boolean updateStatusAndType(ProductBean product){
        boolean result = false ;
        try {
            conn = new MySQLConnection().getConnection();
            pstm = conn.prepareStatement(UPDATE_STATUSTYPE);
            pstm.setString(1, product.getType());
            pstm.setInt(2,product.getStatus());
            pstm.setInt(3,product.getId_products());
            result=  pstm.executeUpdate()==1; //1==1
        }catch (SQLException e){
            Logger.getLogger(ProductDao.class.getName())
                    .log(Level.SEVERE, "Error statusandtype -> ", e);
        } finally {
            closeConnections();
        }
        return result;

    }

    //------------------------Status---------------------------------------------
    private final String UPDATE_STATUS= "UPDATE products SET status = ? WHERE (id_products = ?)";

    public boolean updateStatus(int id,int status){
        boolean result = false ;
        try {
            conn = new MySQLConnection().getConnection();
            pstm = conn.prepareStatement(UPDATE_STATUS);
            pstm.setInt(1,status);
            pstm.setInt(2,id);
            result=  pstm.executeUpdate()==1; //1==1
        }catch (SQLException e){
            Logger.getLogger(ProductDao.class.getName())
                    .log(Level.SEVERE, "Error statusandtype -> ", e);
        } finally {
            closeConnections();
        }
        return result;

    }


    //------------------------------Delete logic ------------------------------------
    //------------------------Status---------------------------------------------
    private final String UPDATE_DELETE= "UPDATE products SET `delete` = ? WHERE (id_products = ?)";

    public void updateDelete(int id, int delete){
        System.out.println(id + "------" + delete +"---------");
        boolean result = false ;
        try {
            conn = new MySQLConnection().getConnection();
            pstm = conn.prepareStatement(UPDATE_DELETE);
            pstm.setInt(1,delete);
            pstm.setInt(2,id);
            result=  pstm.executeUpdate()==1; //1==1
        }catch (SQLException e){
            Logger.getLogger(ProductDao.class.getName())
                    .log(Level.SEVERE, "Error statusandtype -> ", e);
        } finally {
            closeConnections();
        }
        System.out.println(result);
    }




    private final String UPDATE_PRODUCT_GENERAL= "UPDATE  `products` SET `name` = ?, description = ?, description_long = ?, category_id_category = ? WHERE id_products = ?";

    public boolean updateGeneral(ProductBean product){
       // System.out.println(id + "------" + delete +"---------");
        System.out.println(product.toString());
        boolean result = false ;
        try {
            conn = new MySQLConnection().getConnection();
            pstm = conn.prepareStatement(UPDATE_PRODUCT_GENERAL);
            pstm.setString(1,product.getName());
            pstm.setString(2,product.getDescription());
            pstm.setString(3,product.getDescriptionLong());
            pstm.setInt(4,product.getCategory_id_category());
            pstm.setInt(5,product.getId_products());
            result=  pstm.executeUpdate()==1; //1==1
        }catch (SQLException e){
            Logger.getLogger(ProductDao.class.getName())
                    .log(Level.SEVERE, "Error statusandtype -> ", e);
        } finally {
            closeConnections();
        }
        System.out.println(result);
        return result;
    }



    //-----------------------------------------TEST---------------------------------

    public static void main(String[] args) {
        new ProductDao().updateDelete(1,0);

    }


    ///CRSITIAN------------------------------------------------------------------------

   //------------Update to add a offer to an product ------------------------------
    private final String UPDATE_ADD_OFFER= "UPDATE `products` SET `offers_id_offers` = ? WHERE (`id_products` = ?)";

    public boolean updateAddOffer(ProductBean produc){
        boolean result = false ;
        try {
            conn = new MySQLConnection().getConnection();
            pstm = conn.prepareStatement(UPDATE_ADD_OFFER);
            pstm.setInt(1,produc.getOffers_id_offers());
            pstm.setInt(2,produc.getId_products());
            result=  pstm.executeUpdate()==1; //1==1
        }catch (SQLException e){
            Logger.getLogger(ProductDao.class.getName())
                    .log(Level.SEVERE, "Error en updateAddOffer -> ", e);
        } finally {
            closeConnections();
        }
        System.out.println(result);
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


}
