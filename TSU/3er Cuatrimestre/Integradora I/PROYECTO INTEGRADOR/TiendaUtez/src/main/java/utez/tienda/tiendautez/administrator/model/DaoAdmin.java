package utez.tienda.tiendautez.administrator.model;

import utez.tienda.tiendautez.utils.ConexionMysql;
import utez.tienda.tiendautez.utils.MySQLConnection;
import utez.tienda.tiendautez.utils.hash;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoAdmin {

    Connection conn;
    PreparedStatement pstm;
    ResultSet rs;
    hash hash = new hash();
    //Check email and user
    private  final  String QUERY_LOGIN ="SELECT * FROM admins  INNER JOIN roles rol on admins.roles_id_roles=rol.id_roles WHERE admins.email=? AND admins.psw=?";
    private  final  String REMEMBER_PSW ="SELECT * FROM admins  WHERE admins.email=?";


    private final String GET_ADMINS = "SELECT id_admins, username , email ,roles_id_roles, rol FROM admins INNER JOIN roles ON roles_id_roles = id_roles;";
    //"SELECT username ,email ,rol FROM admin INNER JOIN roles ON roles_id_roles = id_roles;"

    private final String  INSERT_ADMIN = "INSERT INTO admins (username, psw, email, roles_id_roles) VALUES (?, md5(?), ?,?);";
    private final String  UPDATE_ADMIN = "UPDATE admins  SET username = ?, psw = md5(?), email = ?, roles_id_roles = ? WHERE id_admins = ?;";
    private final String FIND_ADMIN = "SELECT * FROM admins WHERE id_admins = ?;";
    private final String DELETE_ADMIN = "DELETE FROM admins WHERE id_admins = ?;";

    //-----------------------------------Login admins---------------------------------------------
    public AdminBean loginAdmin(AdminBean admin){
        try {
            conn = new MySQLConnection().getConnection();
            pstm = conn.prepareStatement(QUERY_LOGIN);
            pstm.setString(1, admin.getEmail());
            pstm.setString(2, hash.getMD5(admin.getPsw()));
            rs = pstm.executeQuery();
            if (rs.next()){
                admin = new AdminBean(rs.getInt("id_admins"), rs.getString("username"), rs.getString("email"), rs.getInt("id_roles") ,rs.getString("rol"));
            }else  admin = new AdminBean();
        }catch (SQLException e){
            System.out.println(e);
        }finally {
            System.err.println("Cerrar conexion");
        }
        System.out.println(admin.getEmail()!=null);
        return admin;

    }
    //-----------------------------end Login Admin -------------------------------------------------
    public boolean rememberPsw(AdminBean admin){
        boolean contr = false;
        try {
            conn = new MySQLConnection().getConnection();
            pstm = conn.prepareStatement(REMEMBER_PSW);
            pstm.setString(1, admin.getEmail());
            rs = pstm.executeQuery();
            if (rs.next()){
                contr = true;
            }else {
                contr= false;
            }
        }catch (SQLException e){
            System.out.println(e);
        }
        return contr;

    }


    public List<BeanAdmin> showAdmins (){
        List<BeanAdmin> adminList = new LinkedList<>();
        BeanAdmin admin = null;

        try {
            conn = new MySQLConnection().getConnection();
            String query = GET_ADMINS;
            pstm = conn.prepareStatement(query);
            rs = pstm.executeQuery();
            while (rs.next()){
                admin = new BeanAdmin();
                admin.setId_admins(rs.getLong("id_admins"));
                admin.setUsername(rs.getString("username"));
                admin.setEmail(rs.getString("email"));
                admin.setRoles_id_roles(rs.getLong("roles_id_roles"));
                admin.setRol(rs.getString("rol"));
                //admin.setPsw(rs.getString("psw"));
                //admin.setIdRole(rs.getLong("idRole"));
                //admin.setNameRole(rs.getString("nameRole"));
                adminList.add(admin);
            }
    }catch (SQLException e){
            Logger.getLogger(DaoAdmin.class.getName()).log(Level.SEVERE, "Error en showAdmins -> ", e);
        }finally {
            closeConnections();
        }

        return adminList;
    }

    public boolean saveAdmin(BeanAdmin admin){
        try {
            conn = new MySQLConnection().getConnection();
            String query = INSERT_ADMIN;
            pstm = conn.prepareStatement(query);
            pstm.setString(1,admin.getUsername());
            pstm.setString(2,admin.getPsw());
            pstm.setString(3,admin.getEmail());
            pstm.setLong(4,admin.getRoles_id_roles());
            return pstm.executeUpdate()==1;
        }catch (SQLException e){
            Logger.getLogger(DaoAdmin.class.getName()).log(Level.SEVERE, "Error en saveAdmin -> ", e);
            return false;
        }finally {
            closeConnections();
        }
    }

    public BeanAdmin findAdmin(Long id_admins){
        try {
            conn = new MySQLConnection().getConnection();
            String query = FIND_ADMIN;
            pstm = conn.prepareStatement(query);
            pstm.setLong(1,id_admins);
            rs = pstm.executeQuery();
            while (rs.next()){
                BeanAdmin admin = new BeanAdmin();
                admin.setId_admins(rs.getLong("id_admins"));
                admin.setUsername(rs.getString("username"));
                admin.setEmail(rs.getString("email"));
                admin.setRoles_id_roles(rs.getLong("roles_id_roles"));
                admin.setPsw(rs.getString("psw"));
                //admin.setIdRole(rs.getLong("idRole"));
                //admin.setNameRole(rs.getString("nameRole"));
                return admin;
            }
        }catch (SQLException e){
            Logger.getLogger(DaoAdmin.class.getName()).log(Level.SEVERE, "Error en findAdmins -> ", e);
        }finally {
            closeConnections();
        }

        return null;
    }
    public boolean updateAdmin(BeanAdmin admin){
        try {
            conn = new MySQLConnection().getConnection();
            String query = UPDATE_ADMIN;
            pstm = conn.prepareStatement(query);
            pstm.setString(1,admin.getUsername());
            pstm.setString(2,admin.getPsw());
            pstm.setString(3,admin.getEmail());
            pstm.setLong(4,admin.getRoles_id_roles());
            pstm.setLong(5,admin.getId_admins());
            return pstm.executeUpdate()==1;
        }catch (SQLException e){
            Logger.getLogger(DaoAdmin.class.getName()).log(Level.SEVERE, "Error en updateAdmin -> ", e);
            return false;
        }finally {
            closeConnections();
        }
    }
    public boolean deleteAdmin(Long id_admins){
        try {
            conn = new MySQLConnection().getConnection();
            String query = DELETE_ADMIN;
            pstm = conn.prepareStatement(query);
            pstm.setLong(1, id_admins);
            return pstm.executeUpdate()==1; //1==1
        }catch (SQLException e){
            Logger.getLogger(DaoAdmin.class.getName())
                    .log(Level.SEVERE, "Error deleteAdmin -> ", e);
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



    //-------------------------Prube-------------------------------------------------------------
    public static void main(String[] args) {

        //login
        AdminBean dao = new AdminBean("root@utez.edu.mx","123");
        new DaoAdmin().loginAdmin(dao);


        //Test search
        //new AdminDao().watchAdmins();

        //Test crete
      /* AdminBean dao = new AdminBean("AlexxJoel","joel@utez.edu.mx","123",2);
       Boolean res = new  AdminDao().registerAdmin(dao);
       System.out.println(res);*/

        // Test to find just one
        //new AdminDao().findOneAdmin("3");


        //test update
        /*AdminBean dao = new AdminBean(3,"Jose","mike@gmail.com",1,null);
        dao.setPsw("123456");
        Boolean res = new  AdminDao().updateAdmin(dao);
        System.out.println(res);*/

        //Test delete
        //new AdminDao().deleteAdmin(3);



    }

    /*public void msj(){
        System.out.println("Mensjae" + new Date());
    }
    public static void main(String[] args) {
        Timer timer = new Timer();
        TimerTask tarea = new TimerTask() {
            @Override
            public void run() {
                System.out.println("msj");
            }


        };
        timer.schedule( tarea , 0 ,1000);

    }*/
}


