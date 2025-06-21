/**
 *
 * @author LuisValladolid
 */
public class clienteSingleton {
      /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
            Singleton varsing1 = Singleton.getInstancia();
            Singleton varsing2 = Singleton.getInstancia();
            
            System.out.println(varsing1 == varsing2 ? "solo hay una instancia" : "el singleton a fallado");
            
            ThreadSafeSingleton tss1 = ThreadSafeSingleton.getInstanciaThread();
            ThreadSafeSingleton tss2 = ThreadSafeSingleton.getInstanciaThread();
            
            System.out.println(tss1 == tss2 ? "solo hay una instancia threadsafe" : "el singleton a fallado");
    }
    
}
