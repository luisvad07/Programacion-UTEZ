/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integradora.clasesabstractas;

/**
 *
 * @author Luis Valladolid
 */
public abstract class FasedeGrupos {
    
    private String cabezaSerie;
    private String posicion2;
    private String posicion3;
    private String posicion4;

    public String getCabezaSerie() {
        return cabezaSerie;
    }

    public void setCabezaSerie(String cabezaSerie) {
        this.cabezaSerie = cabezaSerie;
    }

    public String getPosicion2() {
        return posicion2;
    }

    public void setPosicion2(String posicion2) {
        this.posicion2 = posicion2;
    }

    public String getPosicion3() {
        return posicion3;
    }

    public void setPosicion3(String posicion3) {
        this.posicion3 = posicion3;
    }

    public String getPosicion4() {
        return posicion4;
    }

    public void setPosicion4(String posicion4) {
        this.posicion4 = posicion4;
    }
    
    public abstract void GrupoA();
    public abstract void GrupoB();
    public abstract void GrupoC();
    public abstract void GrupoD();
    public abstract void GrupoE();
    public abstract void GrupoF();
    public abstract void GrupoG();
    public abstract void GrupoH();
    
}
