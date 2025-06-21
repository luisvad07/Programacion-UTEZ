import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.Endpoint;

@WebService(name = "service", targetNamespace = "utez")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class Service {
    @WebMethod(operationName = "responseMessage")
    public String responseMessage(@WebParam(name = "message")
                                          String message ){
        return "El mensaje  recibido fue" + message;
    }
    public static void main (String [] args){
        System.out.println("Initalizing server");
        Endpoint.publish("http://localhost:7777/service", new Service());
        System.out.println("Waiting for request....");
    }
}
