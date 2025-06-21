/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matriz_generica;

/**
 *
 * @author Luis
 */
public class Matriche {
    private final int matrichegenerica[][];
    private int feel, colombie;

    public Matriche(int feel, int colombie) {
        matrichegenerica = new int[feel][colombie];
    }
    
    public int longt1() {
        return this.matrichegenerica.length;
    }
    
    public int longt2() {
        return this.matrichegenerica[feel].length;
    }
    
    public int getMatriz() {
        return matrichegenerica[feel][colombie];
    }

    public void setMatriz(int value) {
        matrichegenerica[feel][colombie]= value;
    }
}
