/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integradora;

import integradora.clasesabstractas.FasedeGrupos;

/**
 *
 * @author Luis Valladolid
 */
public class GruposMundial extends FasedeGrupos { //Herencia

    @Override
    public void GrupoA() {
        String A1 = "Qatar";
        this.setCabezaSerie(A1);
        String A2 = "Ecuador";
        this.setPosicion2(A2);
        String A3 = "Senegal";
        this.setPosicion3(A3);
        String A4 = "Países Bajos";
        this.setPosicion4(A4);
        String GrupoA[] = {this.getCabezaSerie(), this.getPosicion2(), this.getPosicion3(), this.getPosicion4()};
        System.out.println("---- GRUPO A ------");
        for (int i = 0; i < GrupoA.length; i++) {
            System.out.println(GrupoA[i]);
        }
    }

    @Override
    public void GrupoB() {
        String B1 = "Inglaterra";
        this.setCabezaSerie(B1);
        String B2 = "Irán";
        this.setPosicion2(B2);
        String B3 = "Estados Unidos";
        this.setPosicion3(B3);
        String B4 = "Gales";
        this.setPosicion4(B4);
        String GrupoB[] = {this.getCabezaSerie(), this.getPosicion2(), this.getPosicion3(), this.getPosicion4()};
        System.out.println("---- GRUPO B ------");
        for (int i = 0; i < GrupoB.length; i++) {
            System.out.println(GrupoB[i]);
        }
    }

    @Override
    public void GrupoC() {
        String C1 = "Argentina";
        this.setCabezaSerie(C1);
        String C2 = "Arabia Saudita";
        this.setPosicion2(C2);
        String C3 = "México";
        this.setPosicion3(C3);
        String C4 = "Polonia";
        this.setPosicion4(C4);
        String GrupoC[] = {this.getCabezaSerie(), this.getPosicion2(), this.getPosicion3(), this.getPosicion4()};
        System.out.println("---- GRUPO C ------");
        for (int i = 0; i < GrupoC.length; i++) {
            System.out.println(GrupoC[i]);
        }
    }

    @Override
    public void GrupoD() {
        String D1 = "Francia";
        this.setCabezaSerie(D1);
        String D2 = "Australia";
        this.setPosicion2(D2);
        String D3 = "Dinamarca";
        this.setPosicion3(D3);
        String D4 = "Túnez";
        this.setPosicion4(D4);
        String GrupoD[] = {this.getCabezaSerie(), this.getPosicion2(), this.getPosicion3(), this.getPosicion4()};
        System.out.println("---- GRUPO D ------");
        for (int i = 0; i < GrupoD.length; i++) {
            System.out.println(GrupoD[i]);
        }
    }

    @Override
    public void GrupoE() {
        String E1 = "España";
        this.setCabezaSerie(E1);
        String E2 = "Costa Rica";
        this.setPosicion2(E2);
        String E3 = "Alemania";
        this.setPosicion3(E3);
        String E4 = "Japón";
        this.setPosicion4(E4);
        String GrupoE[] = {this.getCabezaSerie(), this.getPosicion2(), this.getPosicion3(), this.getPosicion4()};
        System.out.println("---- GRUPO E ------");
        for (int i = 0; i < GrupoE.length; i++) {
            System.out.println(GrupoE[i]);
        }
    }

    @Override
    public void GrupoF() {
        String F1 = "Bélgica";
        this.setCabezaSerie(F1);
        String F2 = "Canadá";
        this.setPosicion2(F2);
        String F3 = "Marruecos";
        this.setPosicion3(F3);
        String F4 = "Croacia";
        this.setPosicion4(F4);
        String GrupoF[] = {this.getCabezaSerie(), this.getPosicion2(), this.getPosicion3(), this.getPosicion4()};
        System.out.println("---- GRUPO F ------");
        for (int i = 0; i < GrupoF.length; i++) {
            System.out.println(GrupoF[i]);
        }
    }

    @Override
    public void GrupoG() {
        String G1 = "Brasil";
        this.setCabezaSerie(G1);
        String G2 = "Serbia";
        this.setPosicion2(G2);
        String G3 = "Suiza";
        this.setPosicion3(G3);
        String G4 = "Camerún";
        this.setPosicion4(G4);
        String GrupoG[] = {this.getCabezaSerie(), this.getPosicion2(), this.getPosicion3(), this.getPosicion4()};
        System.out.println("---- GRUPO G ------");
        for (int i = 0; i < GrupoG.length; i++) {
            System.out.println(GrupoG[i]);
        }
    }

    @Override
    public void GrupoH() {
        String H1 = "Portugal";
        this.setCabezaSerie(H1);
        String H2 = "Ghana";
        this.setPosicion2(H2);
        String H3 = "Uruguay";
        this.setPosicion3(H3);
        String H4 = "Corea del Sur";
        this.setPosicion4(H4);
        String GrupoH[] = {this.getCabezaSerie(), this.getPosicion2(), this.getPosicion3(), this.getPosicion4()};
        System.out.println("---- GRUPO H ------");
        for (int i = 0; i < GrupoH.length; i++) {
            System.out.println(GrupoH[i]);
        }
    }
    
}
