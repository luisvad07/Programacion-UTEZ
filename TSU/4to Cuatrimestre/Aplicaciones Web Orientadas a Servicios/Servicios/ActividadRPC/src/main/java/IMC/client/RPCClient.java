package IMC.client;

import IMC.server.BeanIMC;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class RPCClient {
    public static void main(String[] args) throws MalformedURLException, XmlRpcException {
        Scanner teclado = new Scanner(System.in);
        String name;
        double weight;
        double height;
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setServerURL(new URL("http://localhost:1200"));
        XmlRpcClient client = new XmlRpcClient();
        client.setConfig(config);
        System.out.println("Ingrese su Nombre: ");
        name = teclado.nextLine();
        System.out.println("Ingrese su Peso (en Kg): ");
        weight = teclado.nextDouble();
        System.out.println("Ingrese su altura (en M): ");
        height = teclado.nextDouble();
        BeanIMC BeanIMC = new BeanIMC(name, weight, height);
        System.out.println("---------DATOS DE LA PERSONA---------");
        System.out.println("Su nombre es: " + BeanIMC.getName());
        System.out.println("Su Peso es: " + BeanIMC.getWeight());
        System.out.println("Su Altura es: " + BeanIMC.getHeight());
        Object[] data = {BeanIMC.getWeight(),BeanIMC.getHeight()};
        Double response = (Double) client.execute("Methods.Callimc", data);
        System.out.println("Result -> Hola "+BeanIMC.getName()+" tu IMC es: "+response);
    }
}
