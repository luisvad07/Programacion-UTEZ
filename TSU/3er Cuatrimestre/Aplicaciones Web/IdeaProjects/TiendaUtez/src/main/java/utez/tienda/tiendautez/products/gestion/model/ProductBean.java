package utez.tienda.tiendautez.products.gestion.model;

import utez.tienda.tiendautez.category.model.CategoryBean;
import utez.tienda.tiendautez.images.model.ImagesBean;
import utez.tienda.tiendautez.offers.model.BeanOffer;

import java.io.InputStream;
import java.util.List;

public class ProductBean {
    private int id_products;
    private String name;
    private String description;
    private String descriptionLong;
    private String type;
    private int status;
    private String imageToShow;
    private InputStream imageToInsert;
    private int delete = 0;
    private CategoryBean category;
    private BeanOffer offer;
    private int offers_id_offers = 0;
    private int category_id_category;
    private List<ImagesBean> imagesSecondaries;
    private List<CombinationPDBean> combinations;


    public ProductBean() {
    }

    public ProductBean(int id_products, String name, String description, String descriptionLong, String type, int status, String imageToShow, InputStream imageToInsert, int delete, CategoryBean category, BeanOffer offer, List<ImagesBean> imagesSecondaries, List<CombinationPDBean> combinations) {
        this.id_products = id_products;
        this.name = name;
        this.description = description;
        this.descriptionLong = descriptionLong;
        this.type = type;
        this.status = status;
        this.imageToShow = imageToShow;
        this.imageToInsert = imageToInsert;
        this.delete = delete;
        this.category = category;
        this.offer = offer;
        this.imagesSecondaries = imagesSecondaries;
        this.combinations = combinations;
    }

    public int getId_products() {
        return id_products;
    }

    public void setId_products(int id_products) {
        this.id_products = id_products;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionLong() {
        return descriptionLong;
    }

    public void setDescriptionLong(String descriptionLong) {
        this.descriptionLong = descriptionLong;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getImageToShow() {
        return imageToShow;
    }

    public void setImageToShow(String imageToShow) {
        this.imageToShow = imageToShow;
    }

    public InputStream getImageToInsert() {
        return imageToInsert;
    }

    public void setImageToInsert(InputStream imageToInsert) {
        this.imageToInsert = imageToInsert;
    }

    public int getDelete() {
        return delete;
    }

    public void setDelete(int delete) {
        this.delete = delete;
    }

    public CategoryBean getCategory() {
        return category;
    }

    public void setCategory(CategoryBean category) {
        this.category = category;
    }

    public BeanOffer getOffer() {
        return offer;
    }

    public void setOffer(BeanOffer offer) {
        this.offer = offer;
    }

    public List<ImagesBean> getImagesSecondaries() {
        return imagesSecondaries;
    }

    public void setImagesSecondaries(List<ImagesBean> imagesSecondaries) {
        this.imagesSecondaries = imagesSecondaries;
    }

    public List<CombinationPDBean> getCombinations() {
        return combinations;
    }

    public void setCombinations(List<CombinationPDBean> combinations) {
        this.combinations = combinations;
    }

    public int getOffers_id_offers() {
        return offers_id_offers;
    }

    public void setOffers_id_offers(int offers_id_offers) {
        this.offers_id_offers = offers_id_offers;
    }

    public int getCategory_id_category() {
        return category_id_category;
    }

    public void setCategory_id_category(int category_id_category) {
        this.category_id_category = category_id_category;
    }

    //------------------------Watch the atributs of a product-------------------


    @Override
    public String toString() {
        return "ProductBean{" +
                "id_products=" + id_products +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", descriptionLong='" + descriptionLong + '\'' +
                ", type='" + type + '\'' +
                ", status=" + status +

                ", delete=" + delete +
                ", category=" + category +
                ", offer=" + offer +
                ", offers_id_offers=" + offers_id_offers +
                ", category_id_category=" + category_id_category +
                ", combinations=" + combinations +
                '}';
    }
}
