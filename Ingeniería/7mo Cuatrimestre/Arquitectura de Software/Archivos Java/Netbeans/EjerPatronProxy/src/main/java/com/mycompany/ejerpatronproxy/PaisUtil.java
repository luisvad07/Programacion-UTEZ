package com.mycompany.ejerpatronproxy;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class PaisUtil {

    private static final List<String> PAISES = Arrays.asList(
            "Mexico", "Canada", "Japon", "Francia", "Estados Unidos", "Alemania", "Italia", "Brasil", "China"
    );

    public static String obtenerPaisAleatorio() {
        Random random = new Random();
        int indicePais = random.nextInt(PAISES.size());
        return PAISES.get(indicePais);
    }
    
    public static String obtenerPaisTeclado() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese el nombre del país en el que se encuentra: ");
        String pais = scanner.next();
        return pais;
    }
}