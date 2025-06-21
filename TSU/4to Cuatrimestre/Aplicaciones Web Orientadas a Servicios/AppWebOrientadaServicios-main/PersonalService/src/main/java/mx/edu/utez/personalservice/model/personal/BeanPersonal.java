package mx.edu.utez.personalservice.model.personal;

import mx.edu.utez.personalservice.model.position.BeanPosition;

public class BeanPersonal {
    Long id;
    String name;
    String surname;
    String lastname;
    String birthday;
    BeanPosition position;
    Double salary;

    public BeanPersonal() {
    }

    public BeanPersonal(Long id, String name, String surname, String lastname, String birthday, BeanPosition position, Double salary) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.lastname = lastname;
        this.birthday = birthday;
        this.position = position;
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public BeanPosition getPosition() {
        return position;
    }

    public void setPosition(BeanPosition position) {
        this.position = position;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }
}
