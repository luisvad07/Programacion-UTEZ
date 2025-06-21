
package History;

import java.sql.Date;

public class BeanHistory {
    private int id;
    private String type;
    private double first_number;
    private double second_number;
    private double result;
    private Date createdAt;

    public BeanHistory() {
    }

    public BeanHistory(int id, String type, double first_number, double second_number, double result) {
        this.id = id;
        this.type = type;
        this.first_number = first_number;
        this.second_number = second_number;
        this.result = result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getFirst_number() {
        return first_number;
    }

    public void setFirst_number(double first_number) {
        this.first_number = first_number;
    }

    public double getSecond_number() {
        return second_number;
    }

    public void setSecond_number(double second_number) {
        this.second_number = second_number;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    
}
