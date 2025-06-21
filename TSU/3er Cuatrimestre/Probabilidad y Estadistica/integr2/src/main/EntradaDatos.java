package main;

import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class EntradaDatos {

    public static List<Integer> Datos = new ArrayList<Integer>();

    public static void main(String[] args) throws IOException {

        //Lee un dato de la consola
        BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Introduzca los valores seguido de ENTER");

        //Convierte el texto en un arreglo
        String[] datos = entrada.readLine().split(",");

        //Pasa los datos Una lista para un mejor manejo
        for (int i = 0; i < datos.length; i++) {
            Datos.add(Integer.parseInt(datos[i]));
        }
        //Calcular numero de intervalos
        Integer numerodatos = Datos.size();
        Integer intervalos = (int) ((Collections.max(Datos) - Collections.min(Datos)) / ((1 + 3.32) * Math.log10(numerodatos)));

        System.out.println("Los datos son: " + Datos.toString());
        System.out.println("El limite inferior es: " + Collections.min(Datos));
        System.out.println("El limite superior es: " + Collections.max(Datos));
        System.out.println("El numero de datos es: " + numerodatos);

        //Calcular el rango
        Integer rango = Collections.max(Datos) - Collections.min(Datos);
        System.out.println("Rango: " + rango);

        System.out.println("--------------------------");

        System.out.println("Numero de Intervalos: " + intervalos);

        System.out.println("--------------------------");

        //Calcular Amplitud de la clase
        Integer amplitud = (int) (Math.round((double) rango / (double) intervalos));
        System.out.println("Amplitud " + intervalos);

        int clases = 1, i;
        for (i = 0; clases <= numerodatos; i++) {
            clases = clases * 2;
        }
        System.out.println("Las clases recomendadas son: " + i + " con " + clases + " datos ");

        //desviacion estandar
        System.out.println("\n=======TABLA DE FRECUENCIAS======");

        System.out.format("+--------+----+----+----+----+----+---------------+%n");
        System.out.format("| Clases | f  | fr | fi | Fi | fx |      fx*x     |%n");
        System.out.format("+--------+----+----+----+----+----+---------------+%n");

        Integer clase_anterior = Collections.min(Datos) - 1;
        Integer frAbs = 0;
        Integer frAcm = 0;
        Double frRel = 0.0;
        Double fx = 0.0;
        Double fx2 = 0.0;
        double ola;
        double media1;
        double media = 0;
        double equis = 0;
        double varianza = 0;
        double varianzaA = 0;
        int mediana = 0;
        double moda = 0;
        Integer c=0;

        //65,98,55,62,79,59,51,90,72,56,85,71,73,63,79,94,80,66,62,70
        for (i = 0; clase_anterior < Collections.max(Datos); i++) {
            Integer nueva_clase = clase_anterior + (intervalos - 1);//intervalos

            Double Ci = ((double) (nueva_clase + clase_anterior) / 2);//punto medio

            frAbs = frecuenciaAbsoluta(clase_anterior, nueva_clase);//frecuencia  
            Integer cont3 = frAbs;
         

            if (frAbs > c) {
                c = frAbs;
                moda = Ci;
            }

            frAcm += frAbs; //frecuencia acumulada

            frRel = (double) frAbs / Datos.size();//frecuencia relativa

            fx = (double) frAbs * Ci; //frecuencia de x
            fx2 = fx * Ci;

            int mitad = datos.length / 2;
            if (datos.length % 2 == 0) {
                mediana = Integer.parseInt(datos[mitad]);
            } else {
                mediana = Integer.parseInt(datos[mitad])/2;
            }

            ola = frAbs * Ci;
            double cont2 = ola;
            media1 = cont2 + ola;
            media = media1 / numerodatos;

            equis = (Ci - media) * (Ci - media);
            varianza = equis * frAbs;
            double cont = varianza;
            varianzaA = (cont + varianza) / numerodatos;

            System.out.println("| " + (clase_anterior) + "," + nueva_clase + " | " + frAbs + " | " + Ci + " | " + frRel + " | " + frAcm + " | " + fx + " | " + fx2 + " | ");

            clase_anterior = nueva_clase;
        }

        System.out.format("+--------+----+----+----+----+----+----+%n");

        //media
        System.out.println("la media es: " + media);

        //mediana
        System.out.println("la mediana es: " + mediana);

        //moda
        System.out.println("la moda es: " + moda);

        //varianza
        System.out.println("la varianza es: " + varianzaA);

    }

    /**
     * Cuenta los numeros que estan dentro de un rango de datos
     */
    private static int frecuenciaAbsoluta(Integer limInf, Integer limSup) {
        int count = 0;
        for (int i = 0; i < Datos.size(); i++) {
            if (Datos.get(i) >= limInf && Datos.get(i) < limSup) {
                count++;
            }
        }
        return count;
    }
}
