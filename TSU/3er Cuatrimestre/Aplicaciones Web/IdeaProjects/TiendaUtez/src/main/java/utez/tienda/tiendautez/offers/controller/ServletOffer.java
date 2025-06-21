package utez.tienda.tiendautez.offers.controller;


import utez.tienda.tiendautez.offers.model.BeanOffer;
import utez.tienda.tiendautez.offers.model.DaoOfferCris;
import utez.tienda.tiendautez.products.gestion.model.ProductBean;
import utez.tienda.tiendautez.products.gestion.model.ProductDao;
import utez.tienda.tiendautez.service.ServiceOffer;
import utez.tienda.tiendautez.utils.ResultAction;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(name = "ServletOffer",
        urlPatterns = {
                "/getOffers",
                "/saveOffer",
                "/createOffer",
                "/getOffer",
                "/updateOffer",
                "/deleteOffer",
                "/updateBanner",
                "/addOffer",
                "/compareDate"

        })
@MultipartConfig(maxFileSize = 1024*1024*100)

public class ServletOffer extends HttpServlet {
    DaoOfferCris crisOffers = new DaoOfferCris();

    String action;
    String urlRedirect = "/getOffers";
    ServiceOffer serviceOffer = new ServiceOffer();
    ProductDao daoProduct = new ProductDao();

    //hacer consultas
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
       // crisOffers.checkDateOffer();
        action = request.getServletPath(); //variable que se llena con las url que  hacen las peticiones
        switch (action){
            case "/getOffers":
                List<BeanOffer> offerList = serviceOffer.showOffer();
                //System.out.println(adminList.size()); aqui puede ver si me esta trayendo los registros
                request.setAttribute("offerList", offerList);
                urlRedirect="/WEB-INF/offers/indexOffer.jsp";
                break;
            case "/createOffer":
                urlRedirect="/WEB-INF/offers/createOffer.jsp";
                break;
            case "/getOffer":
                String id_offers = request.getParameter("id_offers");
                id_offers = (id_offers == null) ? "0" : id_offers;
                //System.out.println("ID PERSON -> "+id);
                try {
                    BeanOffer offer = serviceOffer.findOffer(Long.parseLong(id_offers));
                    System.out.println(offer.getBanner());
                    request.setAttribute("offer", offer);
                    urlRedirect="/WEB-INF/offers/updateOffer.jsp";
                }catch (Exception e){
                    urlRedirect="/getOffers";
                }
                break;
            case "/addOffer":
                List<ProductBean> listProducts = daoProduct.showProducts();
                request.setAttribute("productos",listProducts);
                String id_offers2 = request.getParameter("id_offers");
                id_offers2 = (id_offers2 == null) ? "0" : id_offers2;
                try {
                    BeanOffer offer2 = serviceOffer.findOffer(Long.parseLong(id_offers2));
                    //System.out.println(offer2.getBanner());
                    request.setAttribute("offer", offer2);
                    request.setAttribute("id_offers2", Long.parseLong(id_offers2));
                    urlRedirect="/WEB-INF/offers/addOffer.jsp";
                }catch (Exception e){
                    urlRedirect="/getOffers";
                }
                break;

            case "/compareDate":
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                java.util.Date fechaActual = new java.util.Date();
                String fa = sdf.format(fechaActual);
                List<BeanOffer> offerList2 = serviceOffer.showOffer();
                for (BeanOffer beanOffer : offerList2) {
                    if (beanOffer.getEnd_date().equals(fa)) {
                        System.out.println();
                    }
                }
                 break;

            default:
                urlRedirect="/getOffers";
                break;
        }
        request.getRequestDispatcher(urlRedirect).forward(request,response);

    }

    //hacer inserciones
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       // crisOffers.checkDateOffer();
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        action = request.getServletPath(); //variable que se llena con las url que  hacen las peticiones
        switch (action){
            case "/saveOffer":
                String name = request.getParameter("name");
                String discount = request.getParameter("discount");
                String start_date = request.getParameter("start_date");
                String end_date = request.getParameter("end_date");
                Part filePart = request.getPart("banner");
                InputStream banner = filePart.getInputStream();
                //String banner = request.getParameter("banner");
                try{
                    BeanOffer offer = new BeanOffer();
                    offer.setName(name);
                    offer.setDiscount(Integer.parseInt(discount));
                    Date start_dateSDF = new SimpleDateFormat("yyyy-MM-dd").parse(start_date);
                    offer.setStart_date(start_dateSDF);
                    Date end_dateSDF = new SimpleDateFormat("yyyy-MM-dd").parse(end_date);
                    offer.setEnd_date(end_dateSDF);
                    //offer.setBanner(banner);
                    ResultAction result = serviceOffer.saveOffer(offer, banner);
                    urlRedirect="/getOffers?result="+
                            result.isResult() + "&message=" + result.getMessage()
                            + "&status=" + result.getStatus();

                }catch (ParseException e){
                    e.printStackTrace();
                }
                break;
            case "/updateOffer":
                String name2 = request.getParameter("name");
                String discount2 = request.getParameter("discount");
                String start_date2 = request.getParameter("start_date");
                String end_date2 = request.getParameter("end_date");
                //System.out.println(banner2.);
                //String banner2 = request.getParameter("banner");
                String id_offers = request.getParameter("id_offers");
                //System.out.println(filePart2);
                try{
                    BeanOffer offer2 = new BeanOffer();
                    offer2.setName(name2);
                    offer2.setDiscount(Integer.parseInt(discount2));
                    Date start_dateSDF = new SimpleDateFormat("yyyy-MM-dd").parse(start_date2);
                    offer2.setStart_date(start_dateSDF);
                    Date end_dateSDF = new SimpleDateFormat("yyyy-MM-dd").parse(end_date2);
                    offer2.setEnd_date(end_dateSDF);
                    //offer2.setBanner(banner2);
                    offer2.setId_offers(Long.parseLong(id_offers));

                    ResultAction result2 = serviceOffer.updateOffer(offer2);
                    urlRedirect="/getOffers?result="+
                            result2.isResult() + "&message=" + result2.getMessage()
                            + "&status=" + result2.getStatus();

                }catch (ParseException e){
                    e.printStackTrace();
                }
                break;
            case "/deleteOffer":
                String id_offers2 = request.getParameter("id_offers");
                id_offers2 = (id_offers2 == null)?"0":id_offers2;
                //System.out.println("ID PERSONA -> "+id_admins2);
                ResultAction result3 = serviceOffer.deleteOffer(Long.parseLong(id_offers2));
                urlRedirect="/getOffers?result="+
                        result3.isResult() + "&message=" + result3.getMessage()
                        + "&status=" +result3.getStatus();
                break;
            case "/updateBanner":
                Part filePart3 = request.getPart("banner");
                InputStream banner3 = filePart3.getInputStream();
                //System.out.println(banner2.);
                //String banner2 = request.getParameter("banner");
                String id_offers3 = request.getParameter("id_offers");
                //System.out.println(filePart2);
                try{
                    BeanOffer offer3 = new BeanOffer();
                    //offer2.setBanner(banner2);
                    offer3.setId_offers(Long.parseLong(id_offers3));

                    ResultAction result4 = serviceOffer.updateBanner(offer3 , banner3);
                    urlRedirect="/getOffers?result="+
                            result4.isResult() + "&message=" + result4.getMessage()
                            + "&status=" + result4.getStatus();

                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            default:
                urlRedirect="/getOffers";
                break;
        }
        response.sendRedirect(request.getContextPath() + urlRedirect);
    }



}
