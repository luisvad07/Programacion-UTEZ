package Ejercicio2.server;

public class Methods {
    private double producto;
    private double suma;
    private double promedio;
    
    public String response(double num1, double num2, double num3, double num4){
        producto = num1*num2*num3*num4;
        suma = num1+num2+num3+num4;
        promedio = (num1+num2+num3+num4)/4;
        return "Hola, el producto es " + producto + ",la suma es: " + suma + ", el promedio es: " + promedio;
    }
}
//Flores Santana Pablo Samuel 4A DSM
