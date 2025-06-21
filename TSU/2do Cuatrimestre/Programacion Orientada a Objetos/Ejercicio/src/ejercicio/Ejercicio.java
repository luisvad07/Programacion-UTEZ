/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicio;

import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author clientepreferido
 */
public class Ejercicio {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int ap = -1, i, op, p = 0, NumBuscar, buscar;
        Scanner entrada = new Scanner(System.in);
        boolean existe = false;
        boolean encontrar = false;
        String aux = "";
        int[] Numero = new int[5];
        String[] Nombre = new String[5];
        int[] Edad = new int[5];
        String[] Posicion = new String[5];
        do {
            op = Integer.parseInt(JOptionPane.showInputDialog("Menu\n1.-Registro del jugador\n2.-Consulta General\n3.-Busqueda por Numero de Jugador\n4.-Modificar La Posicion\n5.-Eliminar\n6.-Salida"));
            switch (op) {
                case 1:
                    if (ap != 4) {
                        encontrar = false;
                        NumBuscar = Integer.parseInt(JOptionPane.showInputDialog("Dame Numero de Jugador"));
                        for (i = 0; i <= ap; i++) {
                            if (NumBuscar == Numero[ap]) {
                                encontrar = true;
                            }
                        }//for
                        if (encontrar == false) {
                            ap++;

                            Numero[ap] = NumBuscar;
                            Nombre[ap] = JOptionPane.showInputDialog("Dame Nombre de Jugador");
                            Edad[ap] = Integer.parseInt(JOptionPane.showInputDialog("Dame Edad de Jugador"));
                            p = Integer.parseInt(JOptionPane.showInputDialog("Dame la Posicion\n1.-Delantero\n2.-Portero\n3.-Defensa\n4.-MedioCampista"));
                            if (p == 1) {
                                Posicion[ap] = "Delantero";
                            }
                            if (p == 2) {
                                Posicion[ap] = "Portero";
                            }
                            if (p == 3) {
                                Posicion[ap] = "Defensa";
                            }
                            if (p == 4) {
                                Posicion[ap] = "MedioCampista";
                            }

                        }//if
                        else {
                            JOptionPane.showMessageDialog(null, "El numero de jugador ya existe");
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "No Hay Espacio");
                    }
                    break;
                case 2:
                    if (ap != -1) {
                        aux = "";
                        for (i = 0; i <= ap; i++) {
                            aux = aux + "\nNumero de Jugador: " + Numero[i] + "\nNombre del Jugador: " + Nombre[i] + "\nEdad Del Jugador: " + Edad[i] + "\nPosicion del Jugador: " + Posicion[i] + "\n\n";

                        }//for
                        JOptionPane.showMessageDialog(null, "Consulta General\n\n " + aux);

                    }//if
                    else {
                        JOptionPane.showMessageDialog(null, "No hay Datos");
                    }
                    break;
                case 3:
                    if (ap != -1) {
                        existe = false;
                        buscar = Integer.parseInt(JOptionPane.showInputDialog("Dame Numero a Buscar"));

                        for (i = 0; i <= ap; i++) {
                            if (buscar == (Numero[i])) {
                                existe = true;
                                JOptionPane.showMessageDialog(null, "\nNumero de Jugador: " + Numero[i] + "\n Nombre del Jugador: " + Nombre[i] + "\nEdad del Jugador: " + Edad[i] + "\nPosicion del Jugador: " + Posicion[i] + "\n\n");
                            }//if
                        }//for
                        if (existe == false) {
                            JOptionPane.showMessageDialog(null, "El numero " + buscar + " No Existe");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "No hay Datos");
                    }

                    break;
                case 4:
                    if (ap != -1) {
                        existe = false;
                        buscar = Integer.parseInt(JOptionPane.showInputDialog("Dame Numero de Jugador a Modificar"));
                        for (i = 0; i <= ap; i++) {
                            if (buscar == (Numero[i])) {
                                existe = true;
                                String nuevapo = (JOptionPane.showInputDialog("Dame Nueva Posicion\n1.-Delantero\n2.-Portero\n3.-Defensa\n4.-Mediocampista"));
                                if (p == 1) {
                                    Posicion[ap] = "Delantero";
                                }
                                if (p == 2) {
                                    Posicion[ap] = "Portero";
                                }
                                if (p == 3) {
                                    Posicion[ap] = "Defensa";
                                }
                                if (p == 4) {
                                    Posicion[ap] = "Mediocampista";
                                }
                                Posicion[i] = nuevapo;

                            }//if
                        }//for
                        if (existe == true) {
                            JOptionPane.showMessageDialog(null, "Posicion Modificada  ");
                        } else {
                            JOptionPane.showMessageDialog(null, "El numero " + buscar + " No Existe");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "No Hay Datos");
                    }
                    break;
                case 5:
                    if (ap != -1) {
                        ap--;
                    } else {
                        JOptionPane.showMessageDialog(null, "No hay Registros");
                    }
                    break;
            }//switch

        } while (op != 6);//while
        JOptionPane.showMessageDialog(null, "Fin del Programa");
    }

}
