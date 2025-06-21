/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package example;

import java.util.Scanner;

/**
 *
 * @author CC10
 */
public class Example {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int Voto = 1, Cont1 = 0, Cont2 = 0, Cont3 = 0, Cont4 = 0, Opc = 1, Suma = 0, C1 = 0, C2 = 0, C3 = 0, C4 = 0;

        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese un numero entero positivo:");
        //No=sc.nextInt();

        System.out.println("CANDIDATOS");
        System.out.println("1.- Candidato 1");
        System.out.println("2.- Candidato 2");
        System.out.println("3.- Candidato 3");
        System.out.println("4.- Candidato 4");
        System.out.println("Seleccione candidato...!");
        while (Opc != 0) {

            Opc = sc.nextInt();

            switch (Opc) {
                case 1:
                    Cont1++;

                    break;

                case 2:
                    Cont2++;
                    break;

                case 3:
                    Cont3++;
                    break;

                case 4:
                    Cont4++;
                    break;

            }
        }
        Suma = Cont1 + Cont2 + Cont3 + Cont4;

        C1 = Suma / Cont1 * 10;
        C2 = Suma / Cont2 * 10;
        C3 = Suma / Cont3 * 10;
        C4 = Suma / Cont4 * 10;

        System.out.println("Total de votos contabilizados: " + Suma);
        System.out.println("El Candidato 1 obtuvo el : " + C1 + "% con " + Cont1 + " votos.");
        System.out.println("El Candidato 2 obtuvo el : " + C2 + "% con " + Cont2 + " votos.");
        System.out.println("El Candidato 3 obtuvo el : " + C3 + "% con " + Cont3 + " votos.");
        System.out.println("El Candidato 4 obtuvo el : " + C4 + "% con " + Cont4 + " votos.");
    }

}
