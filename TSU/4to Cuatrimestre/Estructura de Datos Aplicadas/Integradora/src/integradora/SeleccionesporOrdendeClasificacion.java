/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integradora;

import static integradora.Menus.confirmarCambio;
import integradora.clasesabstractas.ClaseAbstracta1;
import java.util.Scanner;

/**
 *
 * @author Luis Valladolid
 */
public class SeleccionesporOrdendeClasificacion extends ClaseAbstracta1 { //Herencia
    
    static Scanner mundial=new Scanner(System.in);
    static SeleccionesSimple selecciones = new SeleccionesSimple();
    

    @Override
    public void Opc1() {
        mundial.useDelimiter("\n");
        System.out.print("Pais/Seleccion: ");
        String seleccion = mundial.next();
        selecciones.agregarNodo(seleccion);
    }

    @Override
    public void Opc2() {
        System.out.print("Pais/Seleccion: ");
        String modseleccion = mundial.next();
        if (confirmarCambio()) {
              selecciones.modificarNodo(modseleccion);
            } else {
                System.out.println("Cambios no guardados!...");
            }  
    }

    @Override
    public void Opc3() {
        try{
            System.out.print("Pais/Seleccion: ");
            String deleteseleccion = mundial.next();
            if (confirmarCambio()) {
              selecciones.eliminarNodo(deleteseleccion);
            } else {
                System.out.println("Cambios no guardados!...");
            }  
        }catch(NullPointerException e){
            System.out.println("Registros Vacios: "+e);
        }
    }

    @Override
    public void Opc4() {
        System.out.print("Pais/Seleccion: ");
        String busqseleccion = mundial.next();
        selecciones.mostrarNodo(busqseleccion);
    }

    @Override
    public void Opc5() {
        selecciones.mostrarListaSimple();
    }

    @Override
    public void Opc6() {
        System.out.println("Regresando....."); 
    }
    
}
