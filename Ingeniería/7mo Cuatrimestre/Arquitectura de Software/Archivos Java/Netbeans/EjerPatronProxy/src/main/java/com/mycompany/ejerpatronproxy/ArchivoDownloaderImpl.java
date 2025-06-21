package com.mycompany.ejerpatronproxy;

public class ArchivoDownloaderImpl implements ArchivoDownloader {
    @Override
    public void descargarArchivo(String archivo) {
        System.out.println("Descargando archivo: " + archivo);
    }
}
