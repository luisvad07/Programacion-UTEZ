import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestLogin {

    // Correctos
    @Test
    public void ValidarOne(){
        System.out.println("Caso de Prueba 1: Validacion Correcta");
        String Usu = "admin";
        String Pwd = "admin123";
        String resultadoesperado = "Usuario Correcto!";
        String resultadoobtenido;
        Login log = new Login();
        resultadoobtenido = log.validarLogin(Usu,Pwd);
        assertEquals(resultadoesperado,resultadoobtenido);
    }

    @Test
    public void ValidarOneChiq(){
        System.out.println("Caso de Prueba 1: Validacion Correcta");
        String Usu = "alumno";
        String Pwd = "alumno123";
        String resultadoesperado = "Usuario Correcto!";
        String resultadoobtenido;
        Login log = new Login();
        resultadoobtenido = log.validarLogin(Usu,Pwd);
        assertEquals(resultadoesperado,resultadoobtenido);
    }

    @Test
    public void ValidarTwo(){
        System.out.println("Caso de Prueba 2: Validacion Correcta");
        String Usu = "3256";
        String Pwd = "afdhfh";
        String resultadoesperado = "Error! No coinciden!";
        String resultadoobtenido;
        Login log = new Login();
        resultadoobtenido = log.validarLogin(Usu,Pwd);
        assertEquals(resultadoesperado,resultadoobtenido);
    }

    // Incorrectos
    @Test
    public void ValidarThree(){
        System.out.println("Caso de Prueba 1: Validacion Incorrecta");
        String Usu = "Closter";
        String Pwd = "foreach";
        String resultadoesperado = "Usuario Correcto!";
        String resultadoobtenido;
        Login log = new Login();
        resultadoobtenido = log.validarLogin(Usu,Pwd);
        assertEquals(resultadoesperado,resultadoobtenido);
    }

    @Test
    public void ValidarFour(){
        System.out.println("Caso de Prueba 2: Validacion Incorrecta");
        String Usu = "admin";
        String Pwd = "admin123";
        String resultadoesperado = "Error! No coinciden!";
        String resultadoobtenido;
        Login log = new Login();
        resultadoobtenido = log.validarLogin(Usu,Pwd);
        assertEquals(resultadoesperado,resultadoobtenido);
    }

}
