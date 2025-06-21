package clientefactorymethod;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */


/**
 *
 * @author LuisValladolid
 */
abstract class Tablero {
    private Juego juego;
    private String[] jugadores;

    public Tablero(String[] jugadores) {
        this.jugadores = jugadores;
    }
    
    abstract Juego crearJuego();

    public void inicializar() {
        this.juego = crearJuego();
        this.juego.start();
    }
    
    public String[] getJugadores() {
        return jugadores;
    }
}
