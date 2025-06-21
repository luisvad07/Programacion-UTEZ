package Ejercicio1.server;

public class Methods {

    public String calImc(String nombre,double peso, double altura){
        double total = peso/(altura* altura);
        String saludo = "Hola " + nombre + " tu imc es de " + total;
        return saludo;
    }
}
//Flores Santana Pablo Samuel 4A DSM
