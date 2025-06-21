package client;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class RPCClient {
    static Scanner s = new Scanner(System.in).useDelimiter("\n");
    static Object[] data = new Object[6];
    public static void main(String[] args) throws MalformedURLException, XmlRpcException {
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setServerURL(new URL("http://localhost:1200"));
        XmlRpcClient client = new XmlRpcClient();
        client.setConfig(config);
        int opc;
        do{
            System.out.println("Generador de CURP");
            System.out.println("Acción a realizar:");
            System.out.println("1.- Generar CURP.\n2.- Consultar persona.\n3.- Salir");
            opc = s.nextInt();
            
            switch (opc){
                case 1:
                    llenar();
                    Object response = client.execute("Methods.llenar",data);
                    System.out.println("Tu curp es: " + response);
                    break;
                case 2:
                    System.out.println("Consultar:");
                    Object[] data1 = {s.next()};
                    Object response1 =  client.execute("Methods.buscar",data1);
                    System.out.println(response1);
                    break;
                case 3:
                    System.out.println("Adios");
                    break;
                default:
                    System.err.println("Opción no valida.");
            } 
        } while (opc!=3);
    }

    public static boolean llenar(){
        try{
            System.out.println("Ingresa tu Nombre:");
            data[0] = s.next();
            System.out.println("Ingresa tu Primer Apellido:");
            data[1] = s.next();
            System.out.println("Ingresa tu segundo Apellido:");
            data[2] = s.next();
            System.out.println("Ingresa tu sexo (Hombre/Mujer):");
            data[3] = s.next();
            System.out.println("Ingresa tu Estado de Nacimiento:");
            System.out.println("AGUASCALIENTES AS\nBAJA CALIFORNIA BC\n" +
                    "BAJA CALIFORNIA SUR BS\nCAMPECHE CC\n" +
                    "COAHUILA CL\nCOLIMA CM\n" +
                    "CHIAPAS CS\nCHIHUAHUA CH\n" +
                    "DISTRITO FEDERAL DF\nDURANGO DG\n" +
                    "GUANAJUATO GT\nGUERRERO GR\n" +
                    "HIDALGO HG\nJALISCO JC\n" +
                    "MÉXICO MC\nMICHOACÁN MN\n" +
                    "MORELOS MS\nNAYARIT NT\n" +
                    "NUEVO LEÓN NL\nOAXACA OC\n" +
                    "PUEBLA PL\nQUERÉTARO QT\n" +
                    "QUINTANA ROO QR\nSAN LUIS POTOSÍ SP\n" +
                    "SINALOA SL\nSONORA SR\n" +
                    "TABASCO TC\nTAMAULIPAS TS\n" +
                    "TLAXCALA TL\nVERACRUZ VZ\n" +
                    "YUCATÁN YN\nZACATECAS ZS\n" +
                    "NACIDO EN EL EXTRANJERO NE");
            data[4] = s.next();
            System.out.println("Ingresa tu fecha de nacimiento (YYYY-MM-DD):");
            data[5] = s.next();
            if (!(data[3].equals("Hombre") || data[3].equals("Mujer")) || ((data[4].toString()).length()>2)){
                System.out.println("Ingresa tus datos correctamente");
                llenar();
            }
        }catch (Exception e){
            System.out.println("Ocurrio un error: " + e);
            llenar();
        }
        return true;
    }
}
/*
Buenos días a todas y todos

Realizar una aplicación XMLRPC que permita al usuario generar su CURP con los datos de una persona.
Registrar datos de la persona
Consultar datos de una persona por medio de la CURP
Ejemplo:
Moreno Velasquez Miguel Angel / 1998-01-19 / Hombre / Estado de México (MC)/ MORENO VELASQUEZ MIGUEL / 2RANDOM
MOVM980119HMCRLG01

Notas:
Los datos a guardar de la persona deben ser Nombre, Primer Apellido, Segundo Apellido, Sexo, Estado de nacimiento y Fecha de Nacimiento.
Al registrar a la persona debe devolver el CURP generado y guardarlo en la base de datos.
Los dos últimos datos deben ser aleatorios y alfanuméricos.
Subir su proyecto a un repositorio de git y compartir su enlace en esta tarea. **
La CURP generada debe ser única en la base de datos.
Éxito para todas y todos.
*/
