/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examenu1;

import com.db4o.*;//Importar Libreria
import java.util.Scanner;

/**
 *
 * @author Luis Valladolid
 */
public class ExamenU1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner yei = new Scanner(System.in);
        String ruta = "ExamenU1.db4o"; //1. Crear Ruta
        ObjectContainer db = Db4o.openFile(Db4o.newConfiguration(), ruta); //2. Crear instancia de DB40
        
        int cont, opcion;
        String aux = null;
        
        /*Se va a utilizar un vector llamado “Títulos” en el cual se van a almacenar 
        los encabezados de la tabla de registros y este adicionalmente se va a utilizar al momento de mostrar 
        y de solicitar los datos a un registro nuevo.*/
        String titulos[] = {"ID", "Nombre(s)", "Apellidos", "Telefono", "Edad", "Tipo de sangre", "Email", "Carrera", "Promedio"};
        
        //Tabla de Tipos de Sangre
        String[][] tipo_sangre = {{"1", "A+"}, {"2", "B+"}, {"3", "AB+"}, {"4", "O+"}, {"5", "A-"}, {"6", "B-"}, {"7", "AB-"}, {"8", "0-"}};
        Registros registro = new Registros();

        
        /*Todo el codigo debe de estar Encapsulado en el bloque Try Finally*/
        try {         
            int contador;
            String inicioDate[][] = new String[500][9];

            //Columna 1
            inicioDate[0][0] = "1";
            inicioDate[1][0] = "2";
            inicioDate[2][0] = "3";
            inicioDate[3][0] = "4";
            inicioDate[4][0] = "5";
            //Columna 2
            inicioDate[0][1] = "María";
            inicioDate[1][1] = "Luis Eduardo";
            inicioDate[2][1] = "Camila";
            inicioDate[3][1] = "Noah";
            inicioDate[4][1] = "Gabriela";
            //Columna 3
            inicioDate[0][2] = "Martínez Sánches";
            inicioDate[1][2] = "Gonzalez Flores";
            inicioDate[2][2] = "Cárdenas Talamantes";
            inicioDate[3][2] = "Talamantes Gutiérrez";
            inicioDate[4][2] = "Torcazas Díaz";
            //Columna 4
            inicioDate[0][3] = "735852947";
            inicioDate[1][3] = "7771239543";
            inicioDate[2][3] = "7729517664";
            inicioDate[3][3] = "735964660";
            inicioDate[4][3] = "7774681980";
            //Columna 5
            inicioDate[0][4] = "30";
            inicioDate[1][4] = "24";
            inicioDate[2][4] = "15";
            inicioDate[3][4] = "22";
            inicioDate[4][4] = "28";
            //Columna 6
            inicioDate[0][5] = "AB+";
            inicioDate[1][5] = "A+";
            inicioDate[2][5] = "O-";
            inicioDate[3][5] = "B-";
            inicioDate[4][5] = "AB-";
            //Columna 7
            inicioDate[0][6] = "correo1@gmail.com";
            inicioDate[1][6] = "correo2@gmail.com";
            inicioDate[2][6] = "correo3@gmail.com";
            inicioDate[3][6] = "correo4@gmail.com";
            inicioDate[4][6] = "correo5@gmail.com";
            //Columna 8
            inicioDate[0][7] = "DSM";
            inicioDate[1][7] = "IRD";
            inicioDate[2][7] = "IRD";
            inicioDate[3][7] = "DSM";
            inicioDate[4][7] = "DSM";
            //Columna 9
            inicioDate[0][8] = "9.8";
            inicioDate[1][8] = "9";
            inicioDate[2][8] = "7";
            inicioDate[3][8] = "8.6";
            inicioDate[4][8] = "6.9";

            cont = 4;
            int resp;
            System.out.println("¿Estas seguro que deseas guardar los registros?");
            System.out.print("1. Si; 2.No ---> ");
            resp = yei.nextInt();
            if (resp == 1) {
                registro = new Registros(inicioDate[0][0], inicioDate[0][1], inicioDate[0][2], inicioDate[0][3], inicioDate[0][4], inicioDate[0][5], inicioDate[0][6], inicioDate[0][7], inicioDate[0][8]);
                db.store(registro);
                registro = new Registros(inicioDate[1][0], inicioDate[1][1], inicioDate[1][2], inicioDate[1][3], inicioDate[1][4], inicioDate[1][5], inicioDate[1][6], inicioDate[1][7], inicioDate[1][8]);
                db.store(registro);
                registro = new Registros(inicioDate[2][0], inicioDate[2][1], inicioDate[2][2], inicioDate[2][3], inicioDate[2][4], inicioDate[2][5], inicioDate[2][6], inicioDate[2][7], inicioDate[2][8]);
                db.store(registro);
                registro = new Registros(inicioDate[3][0], inicioDate[3][1], inicioDate[3][2], inicioDate[3][3], inicioDate[3][4], inicioDate[3][5], inicioDate[3][6], inicioDate[3][7], inicioDate[3][8]);
                db.store(registro);
                registro = new Registros(inicioDate[4][0], inicioDate[4][1], inicioDate[4][2], inicioDate[4][3], inicioDate[4][4], inicioDate[4][5], inicioDate[4][6], inicioDate[4][7], inicioDate[4][8]);
                db.store(registro);
                System.out.println("Registros guardados correctamente!");
            } else {
                System.out.println("No se realizaron los cambios!");
            }
            
            int opc;
            do {
                System.out.println("Selecciona una opcion");
                System.out.println("1. Agregar Registros");
                System.out.println("2. Consultar");
                System.out.println("3. Modificar");
                System.out.println("4. Eliminar");
                System.out.println("5. Salir");
                System.out.print("Opcion: ");
                opc = yei.nextInt();
                switch (opc) {
                    case 1:
                        yei.nextLine();
                        do {
                            System.out.println("---------Ingresa un nuevo registro----------");
                            cont++;
                                inicioDate[cont][0] = "" + (cont + 1);
                                registro.setId(inicioDate[cont][0]);
                                System.out.println("ID: " + inicioDate[cont][0]);
                                yei.nextLine();
                                System.out.print("Nombre(s): ");
                                String newNom = yei.nextLine();
                                inicioDate[cont][1] = newNom;
                                
                                System.out.print("Apellidos: ");
                                String newApe = yei.nextLine();
                                inicioDate[cont][2] = newApe;
                                
                                System.out.print("Teléfono: ");
                                String newNumber = yei.nextLine();
                                inicioDate[cont][3] = newNumber;
                                
                                System.out.print("Edad: ");
                                String newEdad = yei.nextLine();
                                inicioDate[cont][4] = newEdad;
                                
                                System.out.println("Escoge el Tipo de Sangre");
                                for (int l = 0; l < tipo_sangre.length; l++) {
                                    for (int j = 0; j < tipo_sangre[l].length; j++) {
                                        System.out.print("->" + tipo_sangre[l][j]);
                                    }
                                    System.out.println("");
                                }

                                boolean pasa = false;
                                do {
                                    System.out.print("Ingresa selección: ");
                                    opcion = yei.nextInt();
                                    if (opcion < 9) {
                                        pasa = true;
                                        inicioDate[cont][5] = tipo_sangre[opcion - 1][1];
                                    } else {
                                        System.out.println("Dato incorrecto , intenta nuevamente!");
                                    }
                                } while (!pasa);
                                yei.nextLine();
                                System.out.print("Correo: ");
                                String newEmail = yei.nextLine();
                                inicioDate[cont][6] = newEmail;
                                
                                System.out.print("Carrera: ");
                                String newCarrera = yei.nextLine();
                                inicioDate[cont][7] = newCarrera;
                                
                                System.out.print("Promedio: ");
                                String newPromedio = yei.nextLine();
                                inicioDate[cont][8] = newPromedio;
                                
                                registro = new Registros(inicioDate[cont][0], inicioDate[cont][1], inicioDate[cont][2], inicioDate[cont][3], inicioDate[cont][4], inicioDate[cont][5], inicioDate[cont][6], inicioDate[cont][7], inicioDate[cont][8]);
                                db.store(registro);
                            System.out.print("¿Deseas continuar agregando registros? 1.Si 2.No  ---> "); ///Para seguir agregando registros 
                            resp = yei.nextInt();
                        } while (resp == 1);
                        break;
                    case 2:
                        Registros busqueda = new Registros(null, null, null,null, null, null, null, null, null);
                        ObjectSet resultados = db.queryByExample(busqueda);
                        System.out.println("---TODOS LOS REGISTROS---");
                        for (int k = 0; k < titulos.length; k++) {
                            System.out.print(titulos[k] + "\t | ");
                        }
                        System.out.println("");
                        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------");
                        while (resultados.hasNext()) {
                            System.out.println(resultados.next());
                        }
                        
                        break;
                    case 3:
                        yei.nextLine();
                        System.out.print("Introduzca el registro a modificar (id): ");
                        String idMod = yei.nextLine();
                        Registros busquedaMod = new Registros(idMod, null, null,null, null, null, null, null, null);
                        ObjectSet resultadosMod = db.queryByExample(busquedaMod);
                        while (resultadosMod.hasNext()) {
                            Registros e = (Registros) resultadosMod.next();
                            System.out.println("ID -> " + registro.getId());
                            System.out.println("Que campo deseas modificar?");
                            System.out.println("1. Nombre");
                            System.out.println("2. Apellidos");
                            System.out.println("3. Teléfono");
                            System.out.println("4. Edad");
                            System.out.println("5. Tipo de Sangre");
                            System.out.println("6. Email");
                            System.out.println("7. Carrera");
                            System.out.println("8. Promedio");
                            int o = yei.nextInt();
                            switch (o) {
                                case 1:
                                    yei.nextLine();
                                    System.out.print("Nuevo Nombre: ");
                                    registro.setNombre(yei.nextLine());
                                    break;
                                case 2:
                                    yei.nextLine();
                                    System.out.print("Nuevo Apellido: ");
                                    registro.setApellidos(yei.nextLine());
                                    break;
                                case 3:
                                    yei.nextLine();
                                    System.out.print("Nuevo Teléfono: ");
                                    registro.setTeléfono(yei.nextLine());
                                    break;
                                case 4:
                                    yei.nextLine();
                                    System.out.print("Nuevo Edad: ");
                                    registro.setEdad(yei.nextLine());
                                    break;
                                case 5:
                                    yei.nextLine();
                                    System.out.println("Escoge el Tipo de Sangre");
                                    for (int l = 0; l < tipo_sangre.length; l++) {
                                        for (int j = 0; j < tipo_sangre[l].length; j++) {
                                            System.out.print("->" + tipo_sangre[l][j]);
                                        }
                                        System.out.println("");
                                    }

                                    boolean pasa = false;
                                    do {
                                        System.out.print("Ingresa selección: ");
                                        opcion = yei.nextInt();
                                        if (opcion < 9) {
                                            pasa = true;
                                            registro.setTipo_sangre(tipo_sangre[opcion - 1][1]);
                                        } else {
                                            System.out.println("Dato incorrecto , intenta nuevamente!");
                                        }
                                    } while (!pasa);
                                    break;
                                case 6:
                                    yei.nextLine();
                                    System.out.print("Nuevo Email: ");
                                    registro.setEmail(yei.nextLine());
                                    break;
                                case 7:
                                    yei.nextLine();
                                    System.out.print("Nuevo Carrera: ");
                                    registro.setCarrera(yei.nextLine());
                                    break;
                                case 8:
                                    yei.nextLine();
                                    System.out.print("Nuevo Promedio: ");
                                    registro.setPromedio(yei.nextLine());
                                    break;
                                default:
                                    System.out.println("Error de opcion!");;
                            }
                            db.store(e);
                        }
                        System.out.println("Estudiante modificado correctamente!");
                        break;
                    case 4:
                        yei.nextLine();
                        System.out.print("Introduzca el registro a eliminar (id): ");
                        String idDelete = yei.nextLine();
                        Registros busquedaDelete = new Registros(idDelete, null, null,null, null, null, null, null, null);
                        ObjectSet resultadosDelete = db.queryByExample(busquedaDelete);
                        while (resultadosDelete.hasNext()) {
                            Registros e = (Registros) resultadosDelete.next();
                            db.delete(e);
                        }
                        System.out.println("Estudiante eliminado correctamente!");
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
