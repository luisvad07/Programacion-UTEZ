package utez.tienda.tiendautez.products.normal.controller;

import utez.tienda.tiendautez.offers.model.BeanOffer;
import utez.tienda.tiendautez.offers.model.DaoOffer;
import utez.tienda.tiendautez.offers.model.DaoOfferCris;
import utez.tienda.tiendautez.products.gestion.model.ProductBean;
import utez.tienda.tiendautez.products.gestion.model.ProductDao;
import utez.tienda.tiendautez.products.normal.model.ProductUserBean;
import utez.tienda.tiendautez.products.normal.model.ProductUserDao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ProductsUserServlet", value = "/ProductsUserServlet")
public class ProductsUserServlet extends HttpServlet {
    DaoOfferCris crisOffers = new DaoOfferCris();

    ProductUserDao productUserDao = new ProductUserDao();
    ProductUserBean productUser = new ProductUserBean();
    ProductDao pd = new ProductDao();
    String accion = "inicio";
    String url = "/index.jsp";
    int id = 0;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    accion = req.getParameter("accion");
    accion = accion==null?"inicio":accion;
        ///crisOffers.checkDateOffer();

        System.out.println(accion);
    if (accion.equalsIgnoreCase("inicio")){
        //System.out.println("Carga de productos uder");

        List<ProductUserBean> product = productUserDao.showProductsPublic();
        List<BeanOffer> offers = new DaoOffer().showOffersCheck();
        //System.out.println(product.size());
        req.setAttribute("products", product);
        req.setAttribute("offers", offers);
        //System.out.println(offers.size());

        url = "/index.jsp";


    } else if (accion.equalsIgnoreCase("getProduc")) {
            id = Integer.parseInt(req.getParameter("id"));
            ProductBean product = pd.findProducts(id);
            req.setAttribute("product", product);
            url = "/completeProductView.jsp";


    } else if (accion.equalsIgnoreCase("banner")) {
        int idBanner = Integer.parseInt(req.getParameter("idBanner"));
        List<ProductUserBean> product = productUserDao.banners_objects(idBanner);
        List<BeanOffer> offers = new DaoOffer().showOffers();
        //System.out.println(product.size());
        req.setAttribute("products", product);
        req.setAttribute("offers", offers);
        url = "/index.jsp";

    } else if (accion.equalsIgnoreCase("search")) {

       String search = req.getParameter("buscar");
        //System.out.println("Carga de productos uder");
        List<ProductUserBean> product = productUserDao.searching(search);
        List<BeanOffer> offers = new DaoOffer().showOffers();
        //System.out.println(product.size());
        req.setAttribute("products", product);
        req.setAttribute("offers", offers);
        //System.out.println(offers.size());

        url = "/index.jsp";



    }

        req.getRequestDispatcher(url).forward(req,res);



    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

    }
}
