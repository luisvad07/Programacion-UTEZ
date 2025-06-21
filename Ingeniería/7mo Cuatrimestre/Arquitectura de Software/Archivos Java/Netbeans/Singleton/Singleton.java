/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package singleton;

/**
 *
 * @author CC7
 */
public class Singleton {
    
    private static Singleton instancia_unica;

    private Singleton(){
    }
    
    public static Singleton getInstancia(){
        if(instancia_unica==null){
            instancia_unica = new Singleton();
        }
        return instancia_unica;
    }
}
