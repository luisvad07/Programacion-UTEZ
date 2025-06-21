package client;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import server.BeanCURP;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class clientCURP {
    static Scanner teclado = new Scanner(System.in);
    public static void main(String[] args) throws MalformedURLException, XmlRpcException {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date fecha = null;
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setServerURL(new URL("http://localhost:1200"));
        XmlRpcClient client = new XmlRpcClient();
        client.setConfig(config);
        Object[] data;
        String response;
        String choix;
        do {
            System.out.println("1. Registrar datos de la persona");
            System.out.println("2. Consultar datos de una persona por medio de la CURP");
            System.out.println("3. Salir");
            System.out.print("Selecciona una opcion: ");
            choix = teclado.next();
                switch (Integer.parseInt(choix)) {
                    case 1:
                        System.out.println("REGISTRO DE DATOS");
                        System.out.print("Ingresa tu Nombre: ");
                        String nombre = teclado.next();
                        System.out.print("Ingresa tu Apellido Paterno: ");
                        String apellido_Pat = teclado.next();
                        System.out.print("Ingresa tu Apellido Materno: ");
                        String apellido_Mat = teclado.next();
                        System.out.print("Ingresa tu Sexo: ");
                        String sexo = teclado.next();
                        System.out.print("Ingresa tu Estado de Nacimiento: ");
                        String estado_Nac = teclado.next();
                        System.out.print("Ingresa tu Fecha de Nacimiento con formato dd/MM/yyyy: ");
                        String fecha_Nac = teclado.next();
                        try{
                            fecha = df.parse(fecha_Nac);
                            System.out.println("Fecha " + fecha + " Valida!");
                        } catch (Exception e) {
                            System.out.println("Formato Invalido");
                        }
                        System.out.println("Datos Correctos!");
                        System.out.println("Tu CURP es: ");
                        data = new Object[]{nombre, apellido_Pat, apellido_Mat, sexo, estado_Nac, fecha};
                        response = (String) client.execute("Method.savePerson", data);
                        System.out.println("Result -> "+response);
                        break;
                    case 2:
                        System.out.println("CONSULTA DE DATOS");

                        break;
                    default:
                        System.out.println("No existe esa opcion!");
                }
        } while (!choix.equals("3"));
    }
}
