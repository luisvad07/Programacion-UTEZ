package clientefactorymethod;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */



/**
 *
 * @author LuisValladolid
 */
public class JuegoSolitario implements Juego {
    private String jugador;

    public JuegoSolitario(String jugador) {
        this.jugador = jugador;
    }

    @Override
    public void start() {
        System.out.println("Inicia juego de solitario");
    }
}