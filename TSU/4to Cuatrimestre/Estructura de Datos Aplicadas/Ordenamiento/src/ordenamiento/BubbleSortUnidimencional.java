package ordenamiento;

public class BubbleSortUnidimencional {
    //Ordenar una lista unidimensional
    public static void main(String[] args) {
        //Array con datos
        int[][] array = {
            /*0*/{  12, 9,  4,  99, 120,1,   3,  10},
            /*1*/{  50, 10, 89, 105,505,1024,999,0}
        };
        //Array de 16 Elementos
        int n = array[0].length*2;
        
        int[] arr_temp=new int[n];
        int cont=0;
        //REcorrer el array original
        //PAra guardar los valores en un vector
        for (int i = 0; i <array.length ; i++) {
            for (int j = 0; j < array[i].length; j++) {
                arr_temp[cont]=array[i][j];
                
                System.out.println(arr_temp[cont]);
                cont++;
            }
        }
        
        //Variable auxiliar para guardar el valor a intercambiar
        int temp = 0;
        /*
        Se tiene que recorrer todas las casillas
        */
        for (int i = 0; i < n; i++) {
            /*
            Comparar el valor de la posicion i
            con todos los demas
            */
            for (int j = 1; j < (n - i); j++) {
                //Comparacion de Valores
                if (arr_temp[j - 1] > arr_temp[j]) {
                    temp = arr_temp[j - 1];
                    arr_temp[j - 1] = arr_temp[j];
                    arr_temp[j] = temp;
                }
                
            }
        }
        //Imprimir los resultados
        for (int i = 0; i < arr_temp.length; i++) {
            System.out.print(arr_temp[i] + " ");
        }
    }
}
