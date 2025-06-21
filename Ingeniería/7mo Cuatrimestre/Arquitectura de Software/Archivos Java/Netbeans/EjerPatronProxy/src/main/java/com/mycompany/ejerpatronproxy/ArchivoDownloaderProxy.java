package com.mycompany.ejerpatronproxy;

import java.util.Arrays;
import java.util.List;

public class ArchivoDownloaderProxy implements ArchivoDownloader {
    private final ArchivoDownloader downloader;
    private final List<String> paisesPermitidos = Arrays.asList("México", "Canadá", "Japón", "Francia");

    public ArchivoDownloaderProxy() {
        this.downloader = new ArchivoDownloaderImpl();
    }

    @Override
    public void descargarArchivo(String archivo) {
        // Simular verificación del país
        
        /*String paisActual = obtenerPaisActual();
        if (paisesPermitidos.contains(paisActual)) {
            downloader.descargarArchivo(archivo);
        } else {
            System.out.println("Acceso restringido desde " + paisActual);
        }*/
        
         String paisIngresado = obtenerPaisIngresado();
        if (paisesPermitidos.contains(paisIngresado)) {
            downloader.descargarArchivo(archivo);
        } else {
            System.out.println("Acceso restringido desde " + paisIngresado);
        }
    }

    private String obtenerPaisActual() {
        // Simular lógica para obtener el país actual del usuario
        String pais = PaisUtil.obtenerPaisAleatorio();
        System.out.println("País actual: " + pais);
        return pais;
    }
    
    private String obtenerPaisIngresado() {
        // Simular lógica para obtener el país actual del usuario
        String pais = PaisUtil.obtenerPaisTeclado();
        //System.out.println("País actual: " + pais);
        return pais;
    }
}
