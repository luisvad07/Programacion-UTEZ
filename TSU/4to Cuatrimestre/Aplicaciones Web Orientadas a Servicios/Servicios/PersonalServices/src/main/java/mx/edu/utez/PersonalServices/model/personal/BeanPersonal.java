package mx.edu.utez.PersonalServices.model.personal;

import mx.edu.utez.PersonalServices.model.position.BeanPosition;

public class BeanPersonal {
    Long id;
    String name;
    String surname;
    String lastname;
    String birthday;
    double salary;
    BeanPosition position;

    public BeanPersonal() {
    }

    public BeanPersonal(Long id, String name, String surname, String lastname, String birthday, double salary, BeanPosition position) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.lastname = lastname;
        this.birthday = birthday;
        this.salary = salary;
        this.position = position;
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

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public BeanPosition getPosition() {
        return position;
    }

    public void setPosition(BeanPosition position) {
        this.position = position;
    }
}
