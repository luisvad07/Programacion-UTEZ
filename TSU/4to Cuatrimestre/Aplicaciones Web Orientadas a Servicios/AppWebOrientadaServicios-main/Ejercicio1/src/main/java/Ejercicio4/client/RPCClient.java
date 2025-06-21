package Ejercicio4.client;

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
        int num1,num2,num3,num4,num5;

        System.out.println("-- Ordenar 5 datos --");

        System.out.println("Ingresa 5 valores:");
        num1 = leer.nextInt();
        num2 = leer.nextInt();
        num3 = leer.nextInt();
        num4 = leer.nextInt();
        num5 = leer.nextInt();
        
        Object[] data = {num1,num2,num3,num4,num5};
        Object response = client.execute("Methods.response", data);

        System.out.println(response);
    }
}
//Flores Santana Pablo Samuel 4A DSM
