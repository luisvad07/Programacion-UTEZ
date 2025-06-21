package com.example;

import java.util.Arrays;
import java.util.List;

public class ArchivoDownloaderProxy implements ArchivoDownloader {
    private ArchivoDownloader downloader;
    private List<String> paisesPermitidos = Arrays.asList("Mexico", "Canada", "Japon", "Francia");

    public ArchivoDownloaderProxy() {
        this.downloader = new ArchivoDownloaderImpl();
    }

    @Override
    public void descargarArchivo(String archivo) {
        // Simular verificación del país
        String paisActual = obtenerPaisActual();
        if (paisesPermitidos.contains(paisActual)) {
            downloader.descargarArchivo(archivo);
        } else {
            System.out.println("Acceso restringido desde " + paisActual);
        }
    }

    private String obtenerPaisActual() {
        // Simular lógica para obtener el país actual del usuario
        String pais = PaisUtil.obtenerPaisAleatorio();
        System.out.println("País actual: " + pais);
        return pais;
    }
}
