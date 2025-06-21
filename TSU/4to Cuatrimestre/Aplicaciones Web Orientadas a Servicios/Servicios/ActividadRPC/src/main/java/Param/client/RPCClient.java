package Param.client;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class RPCClient {
    public static void main(String[] args) throws MalformedURLException, XmlRpcException {
        Scanner teclado = new Scanner(System.in);
        int num1, num2;
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setServerURL(new URL("http://localhost:1200"));
        XmlRpcClient client = new XmlRpcClient();
        client.setConfig(config);
        System.out.println("Ingrese el valor no.1 : ");
        num1 = teclado.nextInt();
        System.out.println("Ingrese el valor no.2 : ");
        num2 = teclado.nextInt();


        Object[] data = {num1, num2};
        String response = (String) client.execute("Methods.param", data);
        System.out.println("Result -> La suma del rango de los numeros es: "+response);
    }
}
