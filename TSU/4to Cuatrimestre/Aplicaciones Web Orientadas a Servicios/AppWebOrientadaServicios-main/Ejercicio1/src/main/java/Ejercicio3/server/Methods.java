package Ejercicio3.server;

public class Methods {
    private double producto;
    private double suma;
    private double promedio;
    
    public String response(double num1, double num2){
        double acum = 0.0;
        
        for (double i = num1 + 1; i < num2; i++){
            acum += i;
        }
        return "La suma de los valores dentro del limite es: " + acum;
    }
}
//Flores Santana Pablo Samuel 4A DSM
