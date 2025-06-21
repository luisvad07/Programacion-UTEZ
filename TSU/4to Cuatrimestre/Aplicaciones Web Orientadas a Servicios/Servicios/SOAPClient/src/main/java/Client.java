import javax.xml.ws.Service;

public class Client {
    public static void main(String[] args){
        ServiceService serviceservice = new ServiceService();
        Service service = (Service) serviceservice.getServicePort();

    }
}
