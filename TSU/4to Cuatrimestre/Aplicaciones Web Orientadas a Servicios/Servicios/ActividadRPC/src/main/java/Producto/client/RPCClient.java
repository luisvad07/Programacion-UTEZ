package Producto.client;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class RPCClient {
    public static void main(String[] args) throws MalformedURLException, XmlRpcException {
        Scanner teclado = new Scanner(System.in);
        String Producto;
        double num1, num2, num3;
        double suma = 0, promedio = 0;

        System.out.println("Ingrese el nombre del Producto: ");
        Producto = teclado.nextLine();
        System.out.println("Ingrese el valor no.1 : ");
        num1 = teclado.nextDouble();
        System.out.println("Ingrese el valor no.2 : ");
        num2 = teclado.nextDouble();
        System.out.println("Ingrese el valor no.3 : ");
        num3 = teclado.nextDouble();

        suma = num1 + num2 + num3;
        promedio = suma/3;

        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setServerURL(new URL("http://localhost:1200"));
        XmlRpcClient client = new XmlRpcClient();
        client.setConfig(config);

        Object[] data = {Producto,suma,promedio};
        String response = (String) client.execute("Methods.letrero", data);
        System.out.println("Result -> "+response);
    }
}
