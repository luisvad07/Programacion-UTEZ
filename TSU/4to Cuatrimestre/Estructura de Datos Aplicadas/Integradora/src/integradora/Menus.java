/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integradora;

import static integradora.Integradora.mundial;
import integradora.interfaces.InterfaceMenus;
import java.util.Scanner;

/**
 *
 * @author Luis Valladolid
 */
public class Menus implements InterfaceMenus{ //Herencia

    @Override
    public void caso1() {
        int opc;
        do {
            System.out.println("Opcion 1");
            System.out.println("Selecciona una opción que deseas efectuar: ");
            System.out.println("1. Ver grupos de la Copa Mundial");
            System.out.println("2. Simulador de la Fase de Grupos");
            System.out.println("3. Regresar al Menu Principal");
            opc = mundial.nextInt();
            System.out.println("\n");
            switch (opc) {
                case 1:
                    GruposMundial sorteo = new GruposMundial(); //Clase Abstracta 2
                    System.out.println("Fase de Grupos de la COPA MUNDIAL FIFA QATAR 2022");
                    sorteo.GrupoA();
                    System.out.println("\n");
                    sorteo.GrupoB();
                    System.out.println("\n");
                    sorteo.GrupoC();
                    System.out.println("\n");
                    sorteo.GrupoD();
                    System.out.println("\n");
                    sorteo.GrupoE();
                    System.out.println("\n");
                    sorteo.GrupoF();
                    System.out.println("\n");
                    sorteo.GrupoG();
                    System.out.println("\n");
                    sorteo.GrupoH();
                    System.out.println("\n");
                    break;
                case 2:
                    
                    break;
                case 3:
                    System.out.println("Regresando....");
                    break;  
                default:
                    System.out.println("Opcion Invalida");
                }
                System.out.println("\n");
            } while (opc != 3);
    }

    @Override
    public void caso2() {
        SeleccionesporOrdendeClasificacion selecciones= new SeleccionesporOrdendeClasificacion();//Clase Abstracta 1
        Confederaciones conf = new Confederaciones(); //Interfaz 2
        mundial.useDelimiter("\n");
        int opc;
        do {
            System.out.println("Opcion 2");
            System.out.println("Selecciona una opción que deseas efectuar: ");
            System.out.println("1. Gestionar Selecciones por Orden de Clasificacion");//Lista Simple
            System.out.println("2. Gestionar Selecciones por Confederacion");//Interfaz 2
            System.out.println("3. Regresar al Menu");
            System.out.print("Opcion: ");
            opc = mundial.nextInt();
            System.out.println("\n");
            switch (opc) {
                case 1:
                    int opc2;
                        do {
                            System.out.println("Subopcion 1");
                            System.out.println("Selecciona una opción que deseas efectuar: ");
                            System.out.println("1. Agregar Seleccion");
                            System.out.println("2. Modificar Seleccion");
                            System.out.println("3. Eliminar Seleccion");
                            System.out.println("4. Consultar Seleccion");
                            System.out.println("5. Mostrar Todas las selecciones");
                            System.out.println("6. Regresar al SubMenu");
                            System.out.print("Opcion: ");
                            opc2 = mundial.nextInt();
                            System.out.println("\n");
                            switch (opc2) {
                                case 1:
                                    selecciones.Opc1();
                                    break;
                                case 2:
                                    selecciones.Opc2();
                                    break;
                                case 3:
                                    selecciones.Opc3();
                                    break;
                                case 4:
                                    selecciones.Opc4();
                                    break;
                                case 5:
                                    selecciones.Opc5();
                                    break;    
                                case 6:
                                     selecciones.Opc6();  
                                    break;     
                                default:
                                    System.out.println("Opcion Invalida");
                            }
                            System.out.println("\n");
                        } while (opc2 != 6);
                    break;
                case 2:
                    do {
                        System.out.println("Subopcion 2");
                        System.out.println("Selecciona una opción que deseas efectuar: ");
                        System.out.println("1. AFC");
                        System.out.println("2. CAF");
                        System.out.println("3. CONCACAF");
                        System.out.println("4. CONMEBOL");
                        System.out.println("5. OFC");
                        System.out.println("6. UEFA");
                        System.out.println("7. Regresar al Submenu");
                        System.out.print("Opcion: ");
                        opc2 = mundial.nextInt();
                        System.out.println("\n");
                        switch (opc2) {
                            case 1:
                                conf.AFC();
                                break;
                            case 2:
                                conf.CAF();
                                break;
                            case 3:
                                conf.CONCACAF();
                                break;
                            case 4:
                                conf.CONMEBOL();
                                break;
                            case 5:
                                conf.OFC();
                                break;    
                            case 6:
                                conf.UEFA();  
                                break;
                            case 7:
                                System.out.println("Regresando.....");  
                                break;    
                            default:
                                System.out.println("Opcion Invalida");
                        }
                        System.out.println("\n");
                    } while (opc2 != 7);
                    break;   
                case 3:
                    System.out.println("Saliendo....");    
                    break;     
                default:
                    System.out.println("Opcion Invalida");
            }
            System.out.println("\n");
        } while (opc != 3);
    }

    @Override
    public void caso3() {
        FaseGruposPila GS = new FaseGruposPila();
        mundial.useDelimiter("\n");
        int opc;
        do {
            System.out.println("Opcion 3");
            System.out.println("Selecciona una opción que deseas efectuar: ");
            System.out.println("1. Agregar Partido");
            System.out.println("2. Modificar Partido");
            System.out.println("3. Eliminar Partido");
            System.out.println("4. Consultar Partido");
            System.out.println("5. Mostrar Todos los partidos");
            System.out.println("6. Regresar al Menu");
            System.out.print("Opcion: ");
            opc = mundial.nextInt();
            System.out.println("\n");
            switch (opc) {
                case 1:
                    System.out.print("Tipo de Partido: ");
                    String tipo_partido = mundial.next();
                    System.out.print("Ingresar Equipo Local: ");
                    String equipo1 = mundial.next();
                    System.out.print("Ingresar Equipo Visitante: ");
                    String equipo2 = mundial.next();
                    System.out.print("Ingresar Resultado: ");
                    String resultado = mundial.next();
                    GS.agregarPila(tipo_partido, equipo1, resultado, equipo2);
                    break;
                case 2:
                    if (confirmarCambio()) {
                      GS.modificarPila();
                    } else {
                       System.out.println("Cambios no guardados!...");
                    }
                    break;
                case 3:
                    try{
                        if (confirmarCambio()) {
                          GS.eliminarPila();
                        } else {
                            System.out.println("Cambios no guardados!...");
                        }  
                    }catch(NullPointerException e){
                        System.out.println("Registros Vacios: "+e);
                    }
                    break;
                case 4:
                    GS.consultarPila();
                    break;
                case 5:
                    GS.mostrarPila();
                    break;    
                case 6:
                    System.out.println("Saliendo....");    
                    break;     
                default:
                    System.out.println("Opcion Invalida");
            }
            System.out.println("\n");
        } while (opc != 6);
    }

    @Override
    public void caso4() {
        FaseFinalCola FF = new FaseFinalCola();
        mundial.useDelimiter("\n");
        int opc;
        do {
            System.out.println("Opcion 4");
            System.out.println("Selecciona una opción que deseas efectuar: ");
            System.out.println("1. Agregar Partido");
            System.out.println("2. Modificar Partido");
            System.out.println("3. Eliminar Partido");
            System.out.println("4. Consultar Partido");
            System.out.println("5. Mostrar Todos los partidos");
            System.out.println("6. Regresar al Menu");
            System.out.print("Opcion: ");
            opc = mundial.nextInt();
            System.out.println("\n");
            switch (opc) {
                case 1:
                    System.out.print("Tipo de Partido: ");
                    String tipo_partido = mundial.next();
                    System.out.print("Ingresar Equipo Local: ");
                    String equipo1 = mundial.next();
                    System.out.print("Ingresar Equipo Visitante: ");
                    String equipo2 = mundial.next();
                    System.out.print("Ingresar Resultado: ");
                    String resultado = mundial.next();
                    FF.agregarCola(tipo_partido, equipo1, resultado, equipo2);
                    break;
                case 2:
                    if (confirmarCambio()) {
                      FF.modificarCola();
                    } else {
                       System.out.println("Cambios no guardados!...");
                    }
                case 3:
                    try{
                        if (confirmarCambio()) {
                          FF.eliminarCola();
                        } else {
                            System.out.println("Cambios no guardados!...");
                        }  
                    }catch(NullPointerException e){
                        System.out.println("Registros Vacios: "+e);
                    }
                    break;
                case 4:
                    FF.consultarCola();
                    break;
                case 5:
                    FF.mostrarCola();
                    break;    
                case 6:
                    System.out.println("Saliendo....");    
                    break;     
                default:
                    System.out.println("Opcion Invalida");
            }
            System.out.println("\n");
        } while (opc != 6);
    }

    @Override
    public void caso5() {
        SedesDoble<String> sedes = new SedesDoble<>();
        mundial.useDelimiter("\n");
        int opc;
        do {
            System.out.println("Opcion 5");
            System.out.println("Selecciona una opción que deseas efectuar: ");
            System.out.println("1. Agregar Estadio");
            System.out.println("2. Modificar Estadio");
            System.out.println("3. Consultar Estadio");
            System.out.println("4. Mostrar Todos los Estadios");
            System.out.println("5. Regresar al Menu");
            System.out.print("Opcion: ");
            opc = mundial.nextInt();
            System.out.println("\n");
            switch (opc) {
                case 1:
                    System.out.print("Ingresa Sede: ");
                    String estadio = mundial.next();
                    sedes.agregarNodo(estadio);
                    break;
                case 2:
                    System.out.print("Ingresa la Sede a Buscar: ");
                    String newEstadio = mundial.next();
                    if (confirmarCambio()) {
                      sedes.modificarNodo(newEstadio);
                    } else {
                        System.out.println("Cambios no guardados!...");
                    }
                    break;
                case 3:
                    System.out.print("Ingresa la Sede a Buscar: ");
                    String busqEstadio = mundial.next();
                    sedes.buscarNodo(busqEstadio);
                    break;
                case 4:
                    sedes.mostrarListaDoble();
                    break;    
                case 5:
                    System.out.println("Saliendo....");    
                    break;     
                default:
                    System.out.println("Opcion Invalida");
            }
            System.out.println("\n");
        } while (opc != 5);
    }

    @Override
    public void caso6() {
        EstadisticasJugadoresCircularSimple EJ = new EstadisticasJugadoresCircularSimple();
        mundial.useDelimiter("\n");
        int opc;
        do {
            System.out.println("Opcion 6");
            System.out.println("Selecciona una opción que deseas efectuar: ");
            System.out.println("1. Agregar Jugador");
            System.out.println("2. Modificar Jugador");
            System.out.println("3. Eliminar Jugador");
            System.out.println("4. Consultar Jugador");
            System.out.println("5. Mostrar Todos los Jugadores");
            System.out.println("6. Regresar al Menu");
            System.out.print("Opcion: ");
            opc = mundial.nextInt();
            System.out.println("\n");
            switch (opc) {
                case 1:
                    mundial.useDelimiter("\n");
                    System.out.println("Ingresa Jugador: ");
                    String nomJugador = mundial.next();
                    System.out.println("Ingresa Pais en el que juega: ");
                    String seleccion = mundial.next();
                    System.out.println("Ingresa Goles: ");
                    int goles = mundial.nextInt();
                    EJ.insertar(nomJugador, seleccion,goles);
                    break;
                case 2:
                    if (confirmarCambio()) {
                        EJ.actualizarDato();
                    } else {
                       System.out.println("Cambios no guardados!...");
                    }
                    break;
                case 3:
                    try{
                        if (confirmarCambio()) {
                        EJ.eliminarPos();
                        } else {
                            System.out.println("Cambios no guardados!...");
                        }  
                    }catch(NullPointerException e){
                        System.out.println("Registros Vacios: "+e);
                    }
                    break;
                case 4:
                    EJ.contiene();
                    break;
                case 5:
                    EJ.imprimir();
                    break;    
                case 6:
                    System.out.println("Saliendo....");    
                    break;     
                default:
                    System.out.println("Opcion Invalida");
            }
            System.out.println("\n");
        } while (opc != 6);
    }

    @Override
    public void caso7() {
        System.out.println("Saliendo de la aplicacion...."); 
    }
    
    public static boolean confirmarCambio() {
        int resp;
        Scanner siuu = new Scanner(System.in);
        System.out.println("\n");
        System.out.println("¿Estas seguro de realizar el cambio?");
        System.out.println("1.- Si");
        System.out.println("2.- No");
        System.out.print("Respuesta: ");
        resp = siuu.nextInt();
        siuu.skip("\n");
        return resp == 1;
    }
    
}
