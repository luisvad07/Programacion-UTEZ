package server;

public class Methods {

    public String suma(String firstnumber, String secondnumber){
        String respuesta = "";
        double suma = Double.parseDouble(firstnumber) + Double.parseDouble(secondnumber);
        respuesta = "La suma de los numeros ingresados es de " + suma;
        return respuesta;
    }

    public String resta(String firstnumber, String secondnumber){
        String respuesta = "";
        double resta = Double.parseDouble(firstnumber) - Double.parseDouble(secondnumber);
        respuesta = "La resta de los numeros ingresados es de " + resta;
        return respuesta;
    }

    public String multiplicacion(String firstnumber, String secondnumber){
        String respuesta = "";
        double multiplicacion = Double.parseDouble(firstnumber) * Double.parseDouble(secondnumber);
        respuesta = "La multiplicacion de los numeros ingresados es de " + multiplicacion;
        return respuesta;
    }

    public String division(String firstnumber, String secondnumber){
        String respuesta = "";
        double division = Double.parseDouble(firstnumber) / Double.parseDouble(secondnumber);
        if (division !=0) {
            respuesta = "La division de los numeros ingresados es de " + division;
        } else {
            respuesta = "Error al dividir entre 0";
        }
        return respuesta;
    }

    public String exponente(String firstnumber, String secondnumber){
        String respuesta = "";
        double exponente= Math.pow(Double.parseDouble(firstnumber),Double.parseDouble(secondnumber));
        respuesta = "El exponente de los numeros ingresados es de " + exponente;
        return respuesta;
    }

    public String raiz(String firstnumber){
        String respuesta = "";
        double raiz = Math.sqrt(Double.parseDouble(firstnumber));
        if (raiz >= 0) {
            respuesta = "La raiz de los numeros ingresados es de " + raiz;
        } else {
            respuesta = "Error al calcular la raiz, ya que uno de los numeros es negativo!";
        }
        return respuesta;
    }
}
