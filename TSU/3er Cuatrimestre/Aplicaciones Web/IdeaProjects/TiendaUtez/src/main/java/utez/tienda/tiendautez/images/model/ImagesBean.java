package utez.tienda.tiendautez.images.model;

import java.io.InputStream;

public class ImagesBean {
    private int id_image ;
    private String images;
    private InputStream inputImages;

    private int products_id_products;

    public ImagesBean() {
    }

    public ImagesBean(int id_image, String images, int products_id_products) {
        this.id_image = id_image;
        this.images = images;
        this.products_id_products = products_id_products;
    }

    public ImagesBean(InputStream inputImages, int products_id_products) {
        this.inputImages = inputImages;
        this.products_id_products = products_id_products;
    }

    public int getId_image() {
        return id_image;
    }

    public void setId_image(int id_image) {
        this.id_image = id_image;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public int getProducts_id_products() {
        return products_id_products;
    }

    public void setProducts_id_products(int products_id_products) {
        this.products_id_products = products_id_products;
    }

    public InputStream getInputImages() {
        return inputImages;
    }

    public void setInputImages(InputStream inputImages) {
        this.inputImages = inputImages;
    }

//------------------------------READ----------------


    @Override
    public String toString() {
        return "ImagesBean{" +
                "id_image=" + id_image +
                ", images='" + images + '\'' +
                ", products_id_products=" + products_id_products +
                '}';
    }
}
