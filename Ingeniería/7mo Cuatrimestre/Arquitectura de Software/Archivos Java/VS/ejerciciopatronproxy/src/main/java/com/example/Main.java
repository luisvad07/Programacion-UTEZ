package com.example;

public class Main {
    public static void main(String[] args) {
        ArchivoDownloader archivoDownloader = new ArchivoDownloaderProxy();

        // Intenta descargar un archivo desde un país permitido
        archivoDownloader.descargarArchivo("archivo.txt");

        // Intenta descargar un archivo desde un país no permitido
        archivoDownloader.descargarArchivo("archivo_restringido.txt");
    }
}