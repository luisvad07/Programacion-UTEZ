/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testcandidatos;

import java.util.Scanner;

/**
 *
 * @author clientepreferido
 */
public class TestCandidatos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner teclado = new Scanner(System.in);
        teclado.useDelimiter("\n");
        String nomb, winner;
        double porcentaje;
        int numVotos = 0, totalVotos;
        int opc;
        System.out.println("----ELECCIONES PARA ALCALDE DE CUERNAVACA------");

        System.out.println("Ingrese el nombre del candidato 1:");
        nomb = teclado.nextLine();
        Candidato person1 = new Candidato(nomb);

        System.out.println("Ingrese el nombre del candidato 2:");
        nomb = teclado.nextLine();
        Candidato person2 = new Candidato(nomb);

        System.out.println("Ingrese el nombre del candidato 3:");
        nomb = teclado.nextLine();
        Candidato person3 = new Candidato(nomb);

        do {
            System.out.println("¿Por cual candidato vas a votar?");
            System.out.println("1.- Candidato 1: " + person1.getNombre() + "\n"
                    + "2.- Candidato 2: " + person2.getNombre() + "\n"
                    + "3.- Candidato 3: " + person3.getNombre() + "\n"
                    + "4.- Calcular ganador \n"
                    + "5.- Salir");
            opc = teclado.nextInt();
            switch (opc) {
                case 1: // Leer Candidato 1
                    person1.aumentarVoto();
                    System.out.println("Has votado por el Candidato "+person1.getNombre());
                    break;
                case 2: // Leer Candidato 2
                    person2.aumentarVoto();
                    System.out.println("Has votado por el Candidato "+person2.getNombre());
                    break;
                case 3: // Leer Candidato 3
                    person3.aumentarVoto();
                    System.out.println("Has votado por el Candidato "+person3.getNombre());
                    break;
                case 4: // Calcular ganador
                    System.out.println("\n");
                    System.out.println("----ELECCIONES PARA ALCALDE DE CUERNAVACA: GANADOOOOR!------");
                    totalVotos = person1.getNumVotos() + person2.getNumVotos() + person3.getNumVotos();
                    if ((person1.getNumVotos() > person2.getNumVotos()) && (person1.getNumVotos() > person3.getNumVotos())) {
                        winner = person1.getNombre();
                        porcentaje = (person1.getNumVotos() * 100) / totalVotos;
                        System.out.println("Ganador: " + winner + ", con: " + porcentaje + "%");
                    } else if ((person2.getNumVotos() > person1.getNumVotos()) && (person2.getNumVotos() > person3.getNumVotos())) {
                        winner = person2.getNombre();
                        porcentaje = (person2.getNumVotos() * 100) / totalVotos;
                        System.out.println("Ganador: " + winner + ", con: " + porcentaje + "%");
                    } else if ((person3.getNumVotos() > person1.getNumVotos()) && (person3.getNumVotos() > person2.getNumVotos())) {
                        winner = person3.getNombre();
                        porcentaje = (person3.getNumVotos() * 100) / totalVotos;
                        System.out.println("Ganador: " + winner + ", con: " + porcentaje + "%");
                    } else {
                        System.out.println("¿Empate?");
                    }
                    System.out.println("\n");
                    break;
                case 5: //Salir del programa
                    System.out.println("Saliendo... GOOD BYE!");
                    break;
                default:
                    System.out.println("Opcion del menu no valida...");
                    break;
            }
        } while (opc != 5);
    }

}
