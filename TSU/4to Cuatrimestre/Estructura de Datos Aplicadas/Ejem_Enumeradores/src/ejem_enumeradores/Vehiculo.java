/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejem_enumeradores;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Luis
 */
public interface Vehiculo {
    Scanner s=new Scanner(System.in);
    ArrayList<Vehiculo> vehiculos = new ArrayList();
    
    public void alta();
    public void baja();
    public void modificacion();
    public void consulta();
}
