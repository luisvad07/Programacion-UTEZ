package clientefactorymethod;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */



/**
 *
 * @author LuisValladolid
 */
public class TableroAjedrez extends Tablero {
    
    public TableroAjedrez(String[] jugadores) {
        super(jugadores);
    }

    @Override
    public Juego crearJuego() {
            String[] jugadores = getJugadores();

            String p1 = "Blanco";
            String p2 = "Negro";

            if (jugadores != null && jugadores.length == 2) {
                p1 = jugadores[0];
                p2 = jugadores[1];
            }

            JuegoAjedrez game = new JuegoAjedrez(p1,p2);
            return game;
    }
}