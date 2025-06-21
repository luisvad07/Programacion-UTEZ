import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import java.util.Random;

@WebService(name = "Services", targetNamespace = "utez")
public class Services {
    Random rd = new Random();

    @WebMethod(operationName = "guessNumber")
    public boolean guessNumber(int number){
        int randomNumber = rd.nextInt(10);
        return number==randomNumber;
    }

    public static void main(String[] args) {
        System.out.println("Inicializando Servidor...");
        Endpoint.publish("http://localhost:7878/Services",new Services());
        System.out.println("Server esta corriendo en http://localhost:7878");
        System.out.println("Waiting requests");
    }
}
