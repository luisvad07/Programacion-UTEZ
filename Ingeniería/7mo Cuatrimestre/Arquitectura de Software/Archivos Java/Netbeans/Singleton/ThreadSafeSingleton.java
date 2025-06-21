/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package singleton;

/**
 *
 * @author CC7
 */
public class ThreadSafeSingleton {
    private volatile static ThreadSafeSingleton unicaInstancia;
    
    private ThreadSafeSingleton(){
    }
    
    public static ThreadSafeSingleton getInstanciaThread(){
        if(unicaInstancia==null){
            synchronized (ThreadSafeSingleton.class) { //Dos hilos al mismo tiempo, uno espera
                if(unicaInstancia==null) {
                    unicaInstancia = new ThreadSafeSingleton();
                }
            }
        }
        return unicaInstancia;
    }
}
