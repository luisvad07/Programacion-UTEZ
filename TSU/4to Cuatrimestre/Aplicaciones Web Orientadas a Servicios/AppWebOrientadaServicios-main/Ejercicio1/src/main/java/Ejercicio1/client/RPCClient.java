package Ejercicio1.client;

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
        String nombre;
        double peso;
        double altura;

        System.out.println("-- CALCULADORA DE IMC --");
        System.out.println("Ingrese su Nombre:");
        nombre = leer.nextLine();
        System.out.println("Ingrese su peso en Kilos:");
        peso = leer.nextDouble();
        System.out.println("Ingrese su estatura en metros:");
        altura = leer.nextDouble();
        Object[] data = {nombre,peso,altura};
        Object response = client.execute("Methods.calImc", data);

//        System.out.println("Hola " + nombre);
        System.out.println(response);
    }
}
//Flores Santana Pablo Samuel 4A DSM
