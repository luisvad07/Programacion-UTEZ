package clientefactorymethod;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */



/**
 *
 * @author LuisValladolid
 */
public class TableroSolitario extends Tablero {
    
    public TableroSolitario(String[] jugadores) {
        super(jugadores);
    }

    @Override
    Juego crearJuego() {
            String[] players = getJugadores();
            String jugador = "Jugador";
            if(players != null && players.length > 0) {
                jugador = players[0];
            }

            JuegoSolitario juego = new JuegoSolitario(jugador);
            return juego;
    }
}
