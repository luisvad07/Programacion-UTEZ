package utez.tienda.tiendautez.service;

import utez.tienda.tiendautez.offers.model.BeanOffer;
import utez.tienda.tiendautez.offers.model.DaoOfferCris;
import utez.tienda.tiendautez.utils.ResultAction;

import java.io.InputStream;
import java.util.List;

public class ServiceOffer {

    DaoOfferCris daoOffer= new DaoOfferCris();


    public List<BeanOffer> showOffer() {
        return daoOffer.showOffers(); //este metodo viene desde el dao
    }
    public ResultAction saveOffer(BeanOffer offer, InputStream banner){
        ResultAction result = new ResultAction();
        if (daoOffer.saveOffer(offer, banner)) {
            result.setResult(true);
            result.setMessage("oferta Registrada Correctamente");
            result.setStatus(200);
        }else {
            result.setResult(false);
            result.setMessage("Error al Registrar oferta");
            result.setStatus(400);
        }
        return result;
    }
    public BeanOffer findOffer(Long id_offers){
        return daoOffer.findOffer(id_offers);
    }

    public ResultAction updateOffer(BeanOffer offer){
        ResultAction result = new ResultAction();
        if (daoOffer.updateOffer(offer)){
            result.setResult(true);
            result.setMessage("oferta modificado correctamente");
            result.setStatus(200);
        }else{
            result.setResult(false);
            result.setMessage("Error al modificar oferta");
            result.setStatus(400);
        }
        return result;
    }
    public ResultAction deleteOffer(Long id_offers){
        ResultAction result = new ResultAction();
        if (daoOffer.deleteOffer(id_offers)) {
            result.setResult(true);
            result.setMessage("Oferta Eliminada Correctamente");
            result.setStatus(200);
        }else {
            result.setResult(false);
            result.setMessage("Error al Oferta Persona");
            result.setStatus(400);
        }
        return result;
    }


    public ResultAction updateBanner(BeanOffer offer, InputStream banner){
        ResultAction result = new ResultAction();
        if (daoOffer.updateBanner(offer, banner)){
            result.setResult(true);
            result.setMessage("Banner de oferta modificado correctamente");
            result.setStatus(200);
        }else{
            result.setResult(false);
            result.setMessage("Error al modificar banner de oferta");
            result.setStatus(400);
        }
        return result;
    }

}
