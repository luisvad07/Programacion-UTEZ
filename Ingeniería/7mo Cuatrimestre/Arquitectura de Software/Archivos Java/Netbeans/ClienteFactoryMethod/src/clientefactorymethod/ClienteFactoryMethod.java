/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package clientefactorymethod;

/**
 *
 * @author CC7
 */
public class ClienteFactoryMethod {

    public static void main(String[] args) {
        
        //Crear tablero de ajedrez.
        String[] jugadores = new String[] {"Luis Valladolid", "Santiago Gimenez"};

        Tablero tableroAjedrez = new TableroAjedrez(jugadores);
        tableroAjedrez.inicializar();
        
        //Crear tablero de solitario
        String[] jugadorSoliario= new String[] { "Jude Belligham" };

        Tablero tableroSolitario = new TableroSolitario(jugadorSoliario);
        tableroSolitario.inicializar();

    }
    
}
