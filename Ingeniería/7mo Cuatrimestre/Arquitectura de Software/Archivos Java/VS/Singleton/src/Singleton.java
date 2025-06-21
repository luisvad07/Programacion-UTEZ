/**
 *
 * @author LuisValladolid
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