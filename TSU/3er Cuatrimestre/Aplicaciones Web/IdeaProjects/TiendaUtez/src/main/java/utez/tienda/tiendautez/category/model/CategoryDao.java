package utez.tienda.tiendautez.category.model;

import utez.tienda.tiendautez.administrator.model.BeanAdmin;
import utez.tienda.tiendautez.administrator.model.DaoAdmin;
import utez.tienda.tiendautez.utils.MySQLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CategoryDao {
    Connection conn;
    PreparedStatement pstm;
    ResultSet rs;

   //-----------------------------FIND ONE CATRGORY ------------------------------------------------------
   private final String QUERY_FIND_CATEGORY = "SELECT * FROM categories WHERE id_category = ?  ";
    private final String QUERY_LIST_CATEGORY = "SELECT * FROM categories   ";

    public CategoryBean findCategory(int id_category){
        CategoryBean category = null;
        try {
            conn = new MySQLConnection().getConnection();

            pstm = conn.prepareStatement(QUERY_FIND_CATEGORY);
            pstm.setInt(1,id_category);
            rs = pstm.executeQuery();
            if (rs.next()){
                category = new CategoryBean(rs.getInt("id_category"), rs.getString("name"));
            }
        }catch (SQLException e){
            Logger.getLogger(DaoAdmin.class.getName()).log(Level.SEVERE, "Error en findCategory -> ", e);
        }finally {
            closeConnections();
        }
       //System.out.println(category.toString());
        return category;
    }

    //--------------------------------------------------------------------------------------------------------
    public List<CategoryBean> allCategories(){
        List<CategoryBean> categoryList = new ArrayList<>();
        try {
            conn = new MySQLConnection().getConnection();
            pstm = conn.prepareStatement(QUERY_LIST_CATEGORY);
            rs = pstm.executeQuery();
            while (rs.next()){

              CategoryBean category = new CategoryBean(rs.getInt("id_category"), rs.getString("name"));
              categoryList.add(category);
            }
        }catch (SQLException e){
            Logger.getLogger(DaoAdmin.class.getName()).log(Level.SEVERE, "Error en findCategory -> ", e);
        }finally {
            closeConnections();
        }


       /* for (CategoryBean c: categoryList         ) {
            System.out.println(c.toString());
        }*/
        return categoryList;
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
        new CategoryDao().allCategories();
    }
}
