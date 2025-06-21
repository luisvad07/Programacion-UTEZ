/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testcandidatos;

/**
 *
 * @author clientepreferido
 */
class Candidato {

    private String nomb;
    private int numVotos;

    public Candidato(String nomb) {
        this.nomb = nomb;
        this.numVotos = numVotos;
    }

    public void aumentarVoto() { //Metodo para incrementar votos
        numVotos = numVotos + 1;
    }

    public void setNombre(String nomb) {
        this.nomb = nomb;
    }

    public void setNumVotos() {
        this.numVotos = numVotos;
    }

    public String getNombre() {
        return nomb;
    }

    public int getNumVotos() {
        return numVotos;
    }

}
