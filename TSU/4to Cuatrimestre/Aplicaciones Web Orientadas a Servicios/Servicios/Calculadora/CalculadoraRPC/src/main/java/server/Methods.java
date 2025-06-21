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
}
