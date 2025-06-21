/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integradora;

import integradora.interfaces.Opcion1;

/**
 *
 * @author Luis Valladolid
 */
public class Confederaciones implements Opcion1 { //Herencia
    
    private final String SeleccionesCONCACAF[] = {"Canadá", "Estados Unidos", "México", "Costa Rica"};
    private final String SeleccionesCONMEBOL[] = {"Brasil", "Argentina", "Ecuador", "Uruguay"};
    private final String SeleccionesUEFA[] = {"Alemania", "Dinamarca", "Bélgica", "Francia", 
        "Croacia", "España", "Serbia", "Inglaterra", "Suiza", "Países Bajos", "Portugal", "Polonia", "Gales"};
    private final String SeleccionesAFC[] = {"Qatar", "Irán", "Corea del Sur", "Japón", 
        "Arabia Saudita", "Australia"};
    private final String SeleccionesCAF[] = {"Ghana", "Senegal", "Túnez", "Marruecos", "Camerún"};

    @Override
    public void CONCACAF() {
        System.out.println("CONCACAF");
        int cont = 1;
        for (int i = 0; i < SeleccionesCONCACAF.length; i++) {
            System.out.println(cont+".- "+SeleccionesCONCACAF[i]);
            cont++;
        }
    }

    @Override
    public void CONMEBOL() {
        System.out.println("CONMEBOL");
        int cont = 1;
        for (int i = 0; i < SeleccionesCONMEBOL.length; i++) {
            System.out.println(cont+".- "+SeleccionesCONMEBOL[i]);
            cont++;
        }
    }

    @Override
    public void UEFA() {
        System.out.println("UEFA");
        int cont = 1;
        for (int i = 0; i < SeleccionesUEFA.length; i++) {
            System.out.println(cont+".- "+SeleccionesUEFA[i]);
            cont++;
        }
    }

    @Override
    public void AFC() {
        System.out.println("AFC");
        int cont = 1;
        for (int i = 0; i < SeleccionesAFC.length; i++) {
            System.out.println(cont+".- "+SeleccionesAFC[i]);
            cont++;
        }
    }

    @Override
    public void CAF() {
        System.out.println("CAF");
        int cont = 1;
        for (int i = 0; i < SeleccionesCAF.length; i++) {
            System.out.println(cont+".- "+SeleccionesCAF[i]);
            cont++;
        }
    }

    @Override
    public void OFC() {
        System.out.println("Ningun Equipo Clasifico");
    }
    
}
