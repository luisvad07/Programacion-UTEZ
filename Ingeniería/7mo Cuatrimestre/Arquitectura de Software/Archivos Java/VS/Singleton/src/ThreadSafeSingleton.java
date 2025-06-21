/**
 *
 * @author LuisValladolid
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
