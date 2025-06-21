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
public abstract class Estadisticas {
    public abstract void insertar(String nomJugador, String Seleccion, int Goles);
    public abstract void imprimir();
    public abstract String eliminarPos();
    public abstract void actualizarDato();
    public abstract void contiene();
}
