package examen.examen.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Vehicle {
    
    private @Id @GeneratedValue Long id;

    
    private String brand, model, serie;
    private int year;

    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public String getSerie() {
        return serie;
    }
    public void setSerie(String serie) {
        this.serie = serie;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    
}
