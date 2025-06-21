package server;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.webserver.WebServer;

import java.io.IOException;

public class serverCURP {

    public static void main(String[] args) throws XmlRpcException, IOException {

        /* Se prepara el inicio del Servidor*/
        System.out.println("Inicio del arranque del Servidor...");
        PropertyHandlerMapping mapping = new PropertyHandlerMapping();
        mapping.addHandler("Method", Method.class);

        /* Aqui se inicia el Servidor*/
        WebServer server = new WebServer(1200);
        server.getXmlRpcServer().setHandlerMapping(mapping);
        server.start();
        System.out.println("En ejecucion...");

    }

}
