package server;

import History.DaoHistory;
import History.BeanHistory;
import java.util.ArrayList;
import java.util.Iterator;

public class Methods {
    DaoHistory daoHistory = new DaoHistory();
    private double resultado;
    public String suma(double firstNUmber,double secondNumber){
        this.resultado = firstNUmber + secondNumber;
        callDao("Suma", firstNUmber, secondNumber, resultado);
        return "Resultado de la suma: " + firstNUmber + " + " + secondNumber + " = " + resultado;
    }

    public String resta(double firstNUmber,double secondNumber){
        this.resultado = firstNUmber - secondNumber;
        callDao("Resta", firstNUmber, secondNumber, resultado);
        return "Resultado de la suma: " + firstNUmber + " - " + secondNumber + " = " + resultado;
    }

    public String multiplicacion(double firstNUmber,double secondNumber){
        this.resultado = firstNUmber * secondNumber;
        callDao("Multiplicación", firstNUmber, secondNumber, resultado);
        return "Resultado de la suma: " + firstNUmber + " x " + secondNumber + " = " + resultado;
    }

    public String division(double firstNUmber,double secondNumber){
        String response = "";

        if (secondNumber != 0){
            this.resultado = firstNUmber / secondNumber;
            callDao("División", firstNUmber, secondNumber, resultado);
            response = "Resultado de la suma: " + firstNUmber + " / " + secondNumber + " = " + resultado;
        }else if(secondNumber == 0){
            response = "No se puede dividir entre cero";
        }

        return response;
    }

    public String exponente(double firstNUmber,double secondNumber){
        this.resultado = Math.pow(firstNUmber,secondNumber);
        callDao("Exponente", firstNUmber, secondNumber, resultado);
        return "Resultado de la suma: " + firstNUmber + " ^ " + secondNumber + " = " + resultado;
    }

    public String raiz(double firstNUmber,double secondNumber){
        String response = "";
        if (firstNUmber >= 0){
            this.resultado = Math.pow(firstNUmber,(1/secondNumber));
            callDao("Raiz", firstNUmber, secondNumber, resultado);
            response = "Resultado de la suma: " + firstNUmber + " ^ 1/" + secondNumber + " = " + resultado;
        }else if(firstNUmber < 0){
            response = "El primer valor no puede ser negativo";
        }
        return response;
    }
    
    public String history(String a){
        String response = "";
        ArrayList<BeanHistory> aux = daoHistory.showOperations();
        for (int i = 0; i < aux.size(); i++){
            response += "#: " + aux.get(i).getId() + "\n"
                    + "Tipo de operación: " + aux.get(i).getType() + "\n"
                    + "Primer número: " + aux.get(i).getFirst_number() + "\n"
                    + "Segundo número: " + aux.get(i).getSecond_number() + "\n"
                    + "Resultado: " + aux.get(i).getResult() + "\n"
                    + "Fecha de creación: " + aux.get(i).getCreatedAt() + "\n";
        }
        
        return response;
    }
    
    public void callDao(String type, double first_number, double second_number, double result){
        daoHistory.saveOperation(type, first_number, second_number, result);
    }
}
