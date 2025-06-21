package mx.edu.utez.Peliculas.model.person;

import java.util.Date;

public class BeanPerson {
    private long id;
    private String name;
    private String description;
    private Date publish_date;
    private String actors;
    private int duration;
    private double ranking;
    private String image;

    public BeanPerson() {
    }

    public BeanPerson(long id, String name, String description, Date publish_date, String actors, int duration, double ranking, String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.publish_date = publish_date;
        this.actors = actors;
        this.duration = duration;
        this.ranking = ranking;
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Date getPublish_date() {
        return publish_date;
    }

    public void setPublish_date(Date publish_date) {
        this.publish_date = publish_date;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getRanking() {
        return ranking;
    }

    public void setRanking(double ranking) {
        this.ranking = ranking;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
