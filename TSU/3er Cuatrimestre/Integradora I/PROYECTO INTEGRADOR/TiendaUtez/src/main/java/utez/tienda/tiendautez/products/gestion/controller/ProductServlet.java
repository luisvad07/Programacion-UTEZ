package utez.tienda.tiendautez.products.gestion.controller;

import com.google.gson.Gson;
import utez.tienda.tiendautez.category.model.CategoryBean;
import utez.tienda.tiendautez.category.model.CategoryDao;
import utez.tienda.tiendautez.images.model.ImagesBean;
import utez.tienda.tiendautez.images.model.ImagesDao;
import utez.tienda.tiendautez.offers.model.DaoOfferCris;
import utez.tienda.tiendautez.products.gestion.model.CombinationPDBean;
import utez.tienda.tiendautez.products.gestion.model.CombinationPDDao;
import utez.tienda.tiendautez.products.gestion.model.ProductBean;
import utez.tienda.tiendautez.products.gestion.model.ProductDao;
import utez.tienda.tiendautez.service.ServiceAdmin;
import utez.tienda.tiendautez.utils.More;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
@MultipartConfig(maxFileSize = 1024*1024*100)
@WebServlet(name = "ProductServlet", value = "/ProductServlet")
public class ProductServlet extends HttpServlet {

    Logger logger = Logger.getLogger("ProductServlet");


    String urlRedirect = "getProducts" ;

    ProductDao daoProduct = new ProductDao();
    CombinationPDDao combProduct = new CombinationPDDao();
    DaoOfferCris crisOffers = new DaoOfferCris();


    ImagesDao imagesDao = new ImagesDao();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        crisOffers.checkDateOffer();

        String accion = req.getParameter("accion");
        int init=0;
        int id_offers = 0 ;
        String id;
        ProductBean products = new ProductBean();
        ProductBean product;
        List<CategoryBean> category;
        switch (accion){
            case "getProducts":
                List<ProductBean> listProducts = daoProduct.showProducts();
                //System.out.println(listProducts.size());
                req.setAttribute("productos",listProducts);
                urlRedirect = "WEB-INF/products/indexProducts.jsp";
                break;

            case "createProducts":
                category =  new CategoryDao().allCategories();
               // System.out.println(category.size());
                req.setAttribute("listCategory", category);
                urlRedirect = "WEB-INF/products/createProduct.jsp";
                break;


            case "seeGeneral":
                 id = req.getParameter("id");
                 product = daoProduct.findProducts(Integer.parseInt(id));
                //System.out.println(                product.toString());
                req.setAttribute("product", product);
                urlRedirect = "WEB-INF/products/createCombinations.jsp";
                break;


            case "seeGeneralUpdate":
                id = req.getParameter("id");
                product = daoProduct.findProducts(Integer.parseInt(id));
                //System.out.println(                product.toString());
                req.setAttribute("product", product);
                urlRedirect = "WEB-INF/products/updateCombinations.jsp";
                break;

            case "updateStatus":
                init = Integer.parseInt(req.getParameter("id"));
                int status = Integer.parseInt(req.getParameter("status"));
                boolean lol = daoProduct.updateStatus(init,status);
                System.out.println(lol);
                urlRedirect = "ProductServlet?accion=getProducts";
                break;

            case "dropRowLogic":
                init = Integer.parseInt(req.getParameter("id"));
                int delete = Integer.parseInt(req.getParameter("delete"));
                daoProduct.updateDelete(init,delete);
                //System.out.println(delete+"    "+init+"----------------------------");
                urlRedirect = "ProductServlet?accion=getProducts";
                break;


            case "findProductUpdate":
                 id = req.getParameter("id");
                category =  new CategoryDao().allCategories();
                // System.out.println(category.size());
                req.setAttribute("listCategory", category);
                 product = daoProduct.findProducts(Integer.parseInt(id));
                //System.out.println(                product.toString());
                req.setAttribute("product", product);
                urlRedirect = "WEB-INF/products/updateProduct.jsp";
                break;

            case "addOfferProduct":
                products.setId_products(Integer.parseInt(req.getParameter("id_products")));
                id_offers = Integer.parseInt(req.getParameter("id_offers"));
                products.setOffers_id_offers(id_offers);
                boolean lolo = daoProduct.updateAddOffer(products);
                System.out.println(lolo);
                urlRedirect = "/addOffer?id_offers="+id_offers;
                break;


            case "deleteOfferProduct":
                products.setId_products(Integer.parseInt(req.getParameter("id_products")));
                id_offers = Integer.parseInt(req.getParameter("id_offers"));
                products.setOffers_id_offers(0);
                boolean lolo1 = daoProduct.updateAddOffer(products);
                System.out.println(lolo1);
                urlRedirect = "/addOffer?id_offers="+id_offers;
                break;


            default:




        }


        req.getRequestDispatcher(urlRedirect).forward(req,res);



    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        res.setCharacterEncoding("UTF-8");
        res.setContentType("text/html");
        crisOffers.checkDateOffer();

        String action = req.getParameter("action");
      // System.out.println(action);
        String url="AdminServlet?accion=login";
        Gson gson = new Gson();
        ProductBean product = null;

        More capital = new More();


            switch (action){
                case "createNormal":
                    product = new ProductBean();
                    product.setName(req.getParameter("nameProducts"));
                    product.setDescription(req.getParameter("shortDescription"));
                    Part mainImage = req.getPart("mainImage");
                    product.setImageToInsert(mainImage.getInputStream());
                    product.setCategory_id_category(Integer.parseInt(req.getParameter("category")));
                    product.setDescriptionLong(req.getParameter("longDescription"));
                    System.out.println(product.getName());
                    int result = daoProduct.addProductGeneral(product);

                    product.setId_products(result);
                    if (result!=0){

                        System.out.println("Inserto Productos e imagenes");

                    }else
                        System.out.println(" No inserto productos e imagenes");
                    url="ProductServlet?accion=seeGeneral&id="+result;
                    break;

                case "createCombinations":
                    product = new ProductBean();
                    product.setType(req.getParameter("type"));
                    product.setStatus(Integer.parseInt(req.getParameter("status")));
                    int idproduct = Integer.parseInt(req.getParameter("id"));
                    product.setId_products(idproduct);

                  //  System.out.println(product.getType());
                   // System.out.println(product.getStatus());
                    //System.out.println(product.getId_products());
                    daoProduct.updateStatusAndType(product);
                    String[] ch = req.getParameterValues("ch[]");
                    String[] md = req.getParameterValues("md[]");
                    String[] gr = req.getParameterValues("gr[]");
                    String[] xgr = req.getParameterValues("xgr[]");
                    String[] simple = req.getParameterValues("simple[]");

                    CombinationPDBean cProduct = null;

                    if (product.getType().equalsIgnoreCase("Simple")){
                        cProduct = gson.fromJson(simple[0],CombinationPDBean.class);
                        combProduct.saveCombination(cProduct,idproduct);

                    }else{
                        for (int i = 0; i < ch.length; i++) {
                            cProduct = gson.fromJson(ch[i],CombinationPDBean.class);
                            combProduct.saveCombination(cProduct,idproduct);

                            cProduct = gson.fromJson(md[i],CombinationPDBean.class);
                            combProduct.saveCombination(cProduct,idproduct);

                            cProduct = gson.fromJson(gr[i],CombinationPDBean.class);
                            combProduct.saveCombination(cProduct,idproduct);

                            cProduct = gson.fromJson(xgr[i],CombinationPDBean.class);
                            combProduct.saveCombination(cProduct,idproduct);
                        }
                    }
                    break;


                case "savePhotoSecundary":
                    int id = Integer.parseInt(req.getParameter("id"));
                    Part image = req.getPart("secondary");
                    ImagesBean  imagesBean4 = new ImagesBean(image.getInputStream(),id);
                    boolean a = imagesDao.addImages(imagesBean4);
                    System.out.println(a);
                    System.out.println(id);
                    url="ProductServlet?accion=seeGeneral&id="+id;
                    break;



                case "updateNormal":
                    product = new ProductBean();
                    product.setName(req.getParameter("nameProducts"));
                    product.setDescription(req.getParameter("shortDescription"));
                    product.setCategory_id_category(Integer.parseInt(req.getParameter("category")));
                    product.setDescriptionLong(req.getParameter("longDescription"));
                    product.setId_products(Integer.parseInt(req.getParameter("id")));


                    boolean bresult = daoProduct.updateGeneral(product);


                    if (bresult){
                        System.out.println("Actualizo Producto ");
                    }else
                        System.out.println(" No update general");

                    url="ProductServlet?accion=seeGeneralUpdate&id="+product.getId_products();
                    break;


                case "updateCombinations":
                    product = new ProductBean();
                    product.setType(req.getParameter("type"));
                    product.setStatus(Integer.parseInt(req.getParameter("status")));
                    int idproducts = Integer.parseInt(req.getParameter("id"));
                    product.setId_products(idproducts);
                    boolean r2 =   combProduct.deleteCombina(product.getId_products());

                    //  System.out.println(product.getType());
                    // System.out.println(product.getStatus());
                    //System.out.println(product.getId_products());
                    boolean r1 = daoProduct.updateStatusAndType(product);
                    if (r1){


                            String[] ch1 = req.getParameterValues("ch[]");
                            String[] md2 = req.getParameterValues("md[]");
                            String[] gr3 = req.getParameterValues("gr[]");
                            String[] xgr4 = req.getParameterValues("xgr[]");
                            String[] simple5 = req.getParameterValues("simple[]");

                            CombinationPDBean cProduct1 = null;

                            if (product.getType().equalsIgnoreCase("Simple")){
                                cProduct1 = gson.fromJson(simple5[0],CombinationPDBean.class);
                                combProduct.saveCombination(cProduct1,idproducts);

                            }else{
                                for (int i = 0; i < ch1.length; i++) {
                                    cProduct1 = gson.fromJson(ch1[i],CombinationPDBean.class);
                                    combProduct.saveCombination(cProduct1,idproducts);

                                    cProduct1 = gson.fromJson(md2[i],CombinationPDBean.class);
                                    combProduct.saveCombination(cProduct1,idproducts);

                                    cProduct1 = gson.fromJson(gr3[i],CombinationPDBean.class);
                                    combProduct.saveCombination(cProduct1,idproducts);

                                    cProduct1 = gson.fromJson(xgr4[i],CombinationPDBean.class);
                                    combProduct.saveCombination(cProduct1,idproducts);
                                }
                            }
                            //--------------------------------

                    }

                    break;

                default:
            }

        res.sendRedirect(url);
    }
}
