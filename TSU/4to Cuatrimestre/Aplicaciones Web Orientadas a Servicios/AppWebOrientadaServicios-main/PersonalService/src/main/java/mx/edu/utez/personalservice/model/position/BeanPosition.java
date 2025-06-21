package mx.edu.utez.personalservice.model.position;

public class BeanPosition {
    Long id;
    String position;
    String description;

    public BeanPosition() {

    }

    public BeanPosition(Long id, String position, String description) {
        this.id = id;
        this.position = position;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
