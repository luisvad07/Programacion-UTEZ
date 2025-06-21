package com.example;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PaisUtil {

    private static final List<String> PAISES = Arrays.asList(
            "Mexico", "Canada", "Japon", "Francia", "Estados Unidos", "Alemania", "Italia", "Brasil", "China"
    );

    public static String obtenerPaisAleatorio() {
        Random random = new Random();
        int indicePais = random.nextInt(PAISES.size());
        return PAISES.get(indicePais);
    }
}