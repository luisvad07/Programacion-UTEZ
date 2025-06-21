import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OperacionesTest {


    // Correctos
    @Test
    public void sumarNumeros(){
        System.out.println("Caso de Prueba 1: Suma de dos numeros");
        int a = 5;
        int b = 7;
        int resultadoesperado = 12;
        int resultadoobtenido;
        Operaciones suma = new Operaciones();
        resultadoobtenido = suma.sumarNumeros(a,b);
        assertEquals(resultadoesperado,resultadoobtenido);
    }

    @Test
    public void restarNumeros(){
        System.out.println("Caso de Prueba 1: Resta de dos numeros");
        int a = 4;
        int b = 2;
        int resultadoesperado = 2;
        int resultadoobtenido;
        Operaciones resta = new Operaciones();
        resultadoobtenido = resta.restarNumeros(a,b);
        assertEquals(resultadoesperado,resultadoobtenido);
    }

    @Test
    public void multiplicarNumeros(){
        System.out.println("Caso de Prueba 1: Multiplicación de dos numeros");
        int a = 5;
        int b = 8;
        int resultadoesperado = 40;
        int resultadoobtenido;
        Operaciones mult = new Operaciones();
        resultadoobtenido = mult.multiplicarNumeros(a, b);
        assertEquals(resultadoesperado,resultadoobtenido);
    }

    @Test
    public void dividirNumeros(){
        System.out.println("Caso de Prueba 1: Suma de dos numeros");
        double a = 7;
        double b = 7;
        double resultadoesperado = 1;
        double resultadoobtenido;
        Operaciones div = new Operaciones();
        resultadoobtenido = div.dividirNumeros(a,b);
        assertEquals(resultadoesperado,resultadoobtenido,0.00);
    }

    //Incorrectos
    @Test
    public void sumarNumerosInco(){
        System.out.println("Caso de Prueba 2: Suma de dos numeros");
        int a = 6;
        int b = 7;
        int resultadoesperado = 12;
        int resultadoobtenido;
        Operaciones suma = new Operaciones();
        resultadoobtenido = suma.sumarNumeros(a,b);
        assertEquals(resultadoesperado,resultadoobtenido);
    }

    @Test
    public void restarNumerosInco(){
        System.out.println("Caso de Prueba 2: Resta de dos numeros");
        int a = 4;
        int b = 1;
        int resultadoesperado = 0;
        int resultadoobtenido;
        Operaciones resta = new Operaciones();
        resultadoobtenido = resta.restarNumeros(a,b);
        assertEquals(resultadoesperado,resultadoobtenido);
    }

    @Test
    public void multiplicarNumerosInco(){
        System.out.println("Caso de Prueba 2: Multiplicación de dos numeros");
        int a = 50;
        int b = 8;
        int resultadoesperado = 100;
        int resultadoobtenido;
        Operaciones mult = new Operaciones();
        resultadoobtenido = mult.multiplicarNumeros(a, b);
        assertEquals(resultadoesperado,resultadoobtenido);
    }

    @Test
    public void dividirNumerosInco(){
        System.out.println("Caso de Prueba 2: Suma de dos numeros");
        double a = 7;
        double b = 6;
        double resultadoesperado = 1;
        double resultadoobtenido;
        Operaciones div = new Operaciones();
        resultadoobtenido = div.dividirNumeros(a,b);
        assertEquals(resultadoesperado,resultadoobtenido,0.00);
    }
}
