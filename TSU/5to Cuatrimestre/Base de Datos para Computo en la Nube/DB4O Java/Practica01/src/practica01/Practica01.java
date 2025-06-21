/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica01;

import com.db4o.*;//Importar Libreria
import java.util.Scanner;

/**
 *
 * @author Luis Valladolid
 */
public class Practica01 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner flor = new Scanner(System.in);
        String ruta = "libros.db4o"; //1. Crear Ruta
        ObjectContainer db = Db4o.openFile(Db4o.newConfiguration(), ruta); //2. Crear instancia de DB40

        /*Todo el codigo debe de estar Encapsulado en el bloque Try Finally*/
        try {
            int opc;
            do {
                System.out.println("Selecciona una opcion");
                System.out.println("1. Agregar");
                System.out.println("2. Consultar");
                System.out.println("3. Consultar y Modificar");
                System.out.println("4. Eliminar");
                System.out.println("5. Salir");
                System.out.print("Opcion: ");
                opc = flor.nextInt();
                switch (opc) {
                    case 1:
                        Libros libros[] = new Libros[10];
                        libros[0] = new Libros("El Señor de los Anillos", "J.R.R. Tolkien", 1954);
                        libros[1] = new Libros("Cien años de soledad", "Gabriel García Márquez", 1967);
                        libros[2] = new Libros("El Hobbit", "J.R.R. Tolkien", 1937);
                        libros[3] = new Libros("1984", "George Orwell", 1949);
                        libros[4] = new Libros("Orgullo y Prejuicio", "Jane Austen", 1813);
                        libros[5] = new Libros("El Principito", "Antoine de Saint-Exupéry", 1943);
                        libros[6] = new Libros("El Alquimista", "Paulo Coelho", 1988);
                        libros[7] = new Libros("El Perfume", "Patrick Süskind", 1985);
                        libros[8] = new Libros("El Señor de los Anillos", "J.R.R. Tolkien", 1954);
                        libros[9] = new Libros("El Nombre de la Rosa", "Umberto Eco", 1980);

                        int resp;
                        System.out.println("¿Estas seguro que deseas guardar los registros?");
                        System.out.print("1. Si; 2.No ---> ");
                        resp = flor.nextInt();
                        if (resp == 1) {
                            for (int i=0;i<libros.length;i++){
                                db.store(libros[i]);
                            }
                            System.out.println("Registros guardados correctamente!");
                        } else {
                            System.out.println("No se realizaron los cambios!");
                        }
                        break;
                    case 2:
                        Libros busqueda = new Libros(null, null, 0);
                        ObjectSet resultados = db.queryByExample(busqueda);
                        System.out.println("---TODOS LOS LIBROS---");
                        while (resultados.hasNext()) {
                            System.out.println(resultados.next());
                        }
                        break;
                    case 3:
                        String lib1 = "El Señor de los Anillos";
                        Libros busq = new Libros(lib1, null, 0);
                        ObjectSet resultadosBusq = db.queryByExample(busq);
                        while (resultadosBusq.hasNext()) {
                            System.out.println(resultadosBusq.next());
                            int r = 0;
                            System.out.println("¿Estas seguro que deseas guardar los registros?");
                            System.out.print("1. Si; 2.No ---> ");
                            r = flor.nextInt();
                            if (r == 1) {
                                String nomMod = "El Señor de los Anillos: La Comunidad del Anillo";
                                    Libros e = (Libros) resultadosBusq.next();
                                    e.setTitulo(nomMod);
                                    db.store(e);
                                System.out.println("Cambios guardados correctamente!");
                            } else {
                                System.out.println("No se realizaron los cambios!");
                            }
                        }
                        
                        String lib2 = "El Hobbit";
                        Libros busq2 = new Libros(lib2, null, 0);
                        ObjectSet resultadosBusq2 = db.queryByExample(busq2);
                        while (resultadosBusq2.hasNext()) {
                            System.out.println(resultadosBusq2.next());
                            int t = 0;
                            System.out.println("¿Estas seguro que deseas guardar los registros?");
                            System.out.print("1. Si; 2.No ---> ");
                            t = flor.nextInt();
                            if (t == 1) {
                                String nomMod2 = "El Hobbit: Un Viaje Inesperado";
                                Libros e2 = (Libros) resultadosBusq2.next();
                                    e2.setTitulo(nomMod2);
                                    db.store(e2);
                                System.out.println("Cambios guardados correctamente!");
                            } else {
                                System.out.println("No se realizaron los cambios!");
                            }
                        }
                        
                        String lib3 = "El Nombre de la Rosa";
                        Libros busq3 = new Libros(lib3, null, 0);
                        ObjectSet resultadosBusq3 = db.queryByExample(busq3);
                        while (resultadosBusq3.hasNext()) {
                            System.out.println(resultadosBusq3.next());
                            int u = 0;
                            System.out.println("¿Estas seguro que deseas guardar los registros?");
                            System.out.print("1. Si; 2.No ---> ");
                            u = flor.nextInt();
                            if (u == 1) {
                                String nomMod3 = "El Silmarillion";
                                String auth = "J.R.R. Tolkien";
                                Libros e3 = (Libros) resultadosBusq3.next();
                                    e3.setTitulo(nomMod3);
                                    e3.setAutor(auth);
                                    db.store(e3);
                                System.out.println("Cambios guardados correctamente!");
                            } else {
                                System.out.println("No se realizaron los cambios!");
                            }
                        }                     
                        System.out.println("Libros consultados correctamente!");

                        break;
                    case 4:
                        String autDelete = "J.R.R. Tolkien";
                        Libros busquedaDelete = new Libros(null, autDelete, 0);
                        ObjectSet resultadosDelete = db.queryByExample(busquedaDelete);
                        while (resultadosDelete.hasNext()) {
                            Libros e = (Libros) resultadosDelete.next();
                            db.delete(e);
                        }
                        System.out.println("Autor eliminado correctamente!");
                        break;
                    case 5:
                        System.out.println("Saliendo de la BD...");
                        break;
                    default:
                        System.out.println("Error de Opcion!");
                }
            } while (opc != 5);
        } finally {
            //Cierre de la BD
            db.close();
        }
    }
    
}
