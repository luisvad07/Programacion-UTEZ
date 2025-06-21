package clientefactorymethod;

/**
 *
 * @author LuisValladolid
 */
public class JuegoAjedrez implements Juego {
    private String jugadorUno;
    private String jugadorDos;

    public JuegoAjedrez(String jugadorUno, String jugadorDos) {
        this.jugadorUno = jugadorUno;
        this.jugadorDos = jugadorDos;
    }

    @Override
    public void start() {
        System.out.println("Inicia juego de ajedrez");
    }
}