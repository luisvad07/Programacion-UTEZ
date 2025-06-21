package IMC.server;

import java.util.Scanner;

public class Methods {

    //Ejemplo
    public double addition(double num1, double num2){
        return num1 + num2;
    }

    //Ejercicio 1
    public double Callimc(double peso, double altura){
        return peso/(altura*altura);
    }

    //Ejercicio 2
    public String letrero(String Producto, double suma, double promedio){
        String mess;
        mess = "Hola, el producto es " + Producto + ", las sumas es: " + suma + " y el promedio es " + promedio;
        return mess;
    }

    //Ejercicio 3
    public int param(int flor, int day) {
        int suma = 0;
        for (int i = flor; i < day; i++) {
            suma += i;
        }
        return suma = flor;
    }

    //Ejercicio 4
    public String array(){

        Scanner teclado = new Scanner(System.in);
        int[] array = new int[5];
        String siu = null;
        int num;

        for (int i=0; i < array.length; i++) {
            System.out.println("Ingrese numero al elemento " + i +" : " );
            num = teclado.nextInt();
        }

        System.out.println("Forma Ascendente");
        for (int i=0; i < array.length; i++) {
            siu = array[i] + " , ";
        }
        return siu;
    }

}
