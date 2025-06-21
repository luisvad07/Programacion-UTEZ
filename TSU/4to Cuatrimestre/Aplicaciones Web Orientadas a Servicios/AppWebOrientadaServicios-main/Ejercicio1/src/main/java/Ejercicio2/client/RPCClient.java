package Ejercicio2.client;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class RPCClient {
    public static void main(String[] args) throws MalformedURLException, XmlRpcException {
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setServerURL(new URL("http://localhost:1200"));
        XmlRpcClient client = new XmlRpcClient();
        client.setConfig(config);

        Scanner leer = new Scanner(System.in);
        
        double num1;
        double num2;
        double num3;
        double num4;

        System.out.println("-- CALCULADORA --");
        System.out.println("Ingrese el primer número:");
        num1 = leer.nextDouble();
        System.out.println("Ingrese el segundo número:");
        num2 = leer.nextDouble();
        System.out.println("Ingrese el tercer número:");
        num3 = leer.nextDouble();
        System.out.println("Ingrese el cuarto número:");
        num4 = leer.nextDouble();
        Object[] data = {num1,num2,num3,num4};
        Object response = client.execute("Methods.response", data);

        System.out.println(response);
    }
}
//Flores Santana Pablo Samuel 4A DSM
