package Ejercicio4.server;

import java.util.Arrays;

public class Methods {    
    public String response(int num1, int num2, int num3, int num4, int num5){
        int[] data = new int[]{num1,num2,num3,num4,num5};
                                
        boolean bandera;
        int aux;
        
        do {
            bandera = false;
            for ( int i = 0; i < data.length-1; i++){
                
                if ( data[i] > data[i+1]){
                    aux = data[i];
                    data[i] = data[i+1];
                    data[i+1] = aux;
                    bandera = true;
                }
            }            
        } while ( bandera == true );
        
        return "El arreglo ordenado es: " + Arrays.toString(data); 
    }
}
//Flores Santana Pablo Samuel 4A DSM
