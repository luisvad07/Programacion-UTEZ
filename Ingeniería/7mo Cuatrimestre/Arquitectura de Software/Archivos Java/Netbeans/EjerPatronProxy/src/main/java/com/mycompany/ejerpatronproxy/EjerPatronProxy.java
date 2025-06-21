/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.ejerpatronproxy;

/**
 *
 * @author CC7
 */
public class EjerPatronProxy {

    public static void main(String[] args) {
        ArchivoDownloader archivoDownloader = new ArchivoDownloaderProxy();

        // Intenta descargar un archivo desde un país
        archivoDownloader.descargarArchivo("archivo.txt");
        
        //archivoDownloader.descargarArchivo("archivo_restringido.txt");
    }
}
