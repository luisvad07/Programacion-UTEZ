/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package utez.edu.mx.examenu1;

import java.util.Scanner;

/**
 *
 * @author Luis Eduardo Bahena Castillo
 */
public class ExamenU1 {
    
    static Scanner florecita = new Scanner(System.in);
            
    enum transportes{
        AVION, BARCO, TREN
    }
    
    enum combus{
        GASNATURAL, GASOLINA, DIESEL
    }

    public static void main(String[] args) {
        Abstract transporte;
        switch (seleccionarTrans()) {
            case AVION:
                transporte = new Avion();
                transporte.ingresarCombustible();
                break;
            case BARCO:
                transporte = new Barco();
                transporte.ingresarCombustible();
                break;
            case TREN:
                transporte = new Tren();
                transporte.ingresarCombustible();
                break;    
            default:
                throw new AssertionError();
        }
        switch (seleccionarCombus()) {
            case GASNATURAL:
                transporte.calcularDistanciaGasNatural();
                break;
            case GASOLINA:
                transporte.calcularDistanciaGasolina();
                break;
            case DIESEL:
                transporte.calcularDistanciaDiesel();
                break;
            default:
                throw new AssertionError();
        }
    }
    
    public static transportes seleccionarTrans() {
        transportes op = null;
        System.out.println("----MENU DE TRANSPORTES-----");
        System.out.println("Ingresa una de las opciones: ");
        System.out.println("AVION");
        System.out.println("TREN");
        System.out.println("BARCO");
        System.out.print("Opcion: ");
        String opc = florecita.next();
        try {
            op = transportes.valueOf(opc.toUpperCase());
        } catch (Exception e){
            System.out.println("\n");
            System.err.println("Error de Opcion!, Vuelva a Ingresar al Menu Principal");
            seleccionarTrans();
        }
        return op;
    }
    
    public static combus seleccionarCombus() {
        combus op = null;
        System.out.println("----MENU DE COMBUSTIBLES-----");
        System.out.println("Ingresa una de las opciones: ");
        System.out.println("GASNATURAL");
        System.out.println("GASOLINA");
        System.out.println("DIESEL");
        System.out.print("Opcion: ");
        String opc = florecita.next();
        try {
            op = combus.valueOf(opc.toUpperCase());
        } catch (Exception e){
            System.out.println("\n");
            System.err.println("Error de Opcion!, Vuelva a Ingresar al Menu Principal");
            seleccionarTrans();
        }
        return op;
    }
}
