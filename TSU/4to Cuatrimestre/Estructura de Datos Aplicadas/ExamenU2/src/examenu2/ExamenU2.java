/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package examenu2;

import java.util.Scanner;

/**
 *
 * @author CC7
 */
public class ExamenU2 {

    /**
     * @param args the command line arguments
     */
    
    static Scanner gali = new Scanner(System.in);
    static int filas,columnas;
    
    public static void main(String[] args) {
        int[][] matrizA = null, matrizB = null;
        int opc;
        do {
            System.out.println("----- ALGEBRA DE MATRICES -----");
            System.out.println("Elige una opción del menu que deseas efectuar");
            System.out.println("1. Llenar Matrices");
            System.out.println("2. Imprimir Matrices ingresadas");
            System.out.println("3. Suma de Matrices");
            System.out.println("4. Resta de Matrices");
            System.out.println("5. Producto de un escalar por una matriz");
            System.out.println("6. Producto de Matrices");
            System.out.println("7. Matriz Transpuesta");
            System.out.print("Opcion: ");
            opc = gali.nextInt();
            switch (opc) {
                
                case 1:
                    System.out.println("\n");
                    System.out.print("¿Cuantos filas quieres darle a la matriz tanto A como B?: ");
                    filas = gali.nextInt();
                    System.out.println("\n");
                    System.out.print("¿Cuantos columnas quieres darle a la matriz tanto A como B?: ");
                    columnas = gali.nextInt();
                    if (filas == columnas) {
                        matrizA = new int[filas][columnas];
                        matrizB = new int[filas][columnas];
                        System.out.println("\n");
                        System.out.println("---- MATRIZ A ----");
                        for (int i = 0; i < matrizA.length; i++) {
                            for (int j = 0; j < matrizA.length; j++) {
                                System.out.print("Ingresa numero en el elemento ["+i+"] ["+j+"] = ");
                                int numA = gali.nextInt();
                                matrizA[i][j]= numA;
                            }
                        }
                        System.out.println("\n");
                        System.out.println("---- MATRIZ B ----");
                        for (int i = 0; i < matrizB.length; i++) {
                            for (int j = 0; j < matrizB.length; j++) {
                                System.out.print("Ingresa numero en el elemento ["+i+"] ["+j+"] = ");
                                int numB = gali.nextInt();
                                matrizB[i][j]= numB;
                            }
                        }
                    } else {
                        System.out.println("La matriz tiene que ser cubica!");
                    }
                    break;
                    
                case 2:
                        System.out.println("\n");
                        System.out.println("---- MATRIZ A ----");
                        for (int i = 0; i < matrizA.length; i++) {
                            for (int j = 0; j < matrizA.length; j++) {
                                System.out.print(" | " + matrizA[i][j] + " | ");
                            }
                            System.out.println("");
                        }
                        System.out.println("\n");
                        System.out.println("---- MATRIZ B ----");
                        for (int i = 0; i < matrizB.length; i++) {
                            for (int j = 0; j < matrizB.length; j++) {
                                System.out.print(" | " + matrizB[i][j] + " | ");
                            }
                            System.out.println("");
                        }
                    break;
                    
                case 3:
                    int[][] matrizResult =new int[filas][columnas];
                    System.out.println("\n");
                    for (int i = 0; i < matrizResult.length; i++) {
                        for (int j = 0; j < matrizResult.length; j++) {
                            matrizResult[i][j]= matrizA[i][j]+matrizB[i][j];
                        }
                    }
                    System.out.println("\n");
                    System.out.println("---- MATRIZ RESULTANTE ----");
                    for (int i = 0; i < matrizResult.length; i++) {
                        for (int j = 0; j < matrizResult.length; j++) {
                            System.out.println(" | " + matrizResult[i][j] + " | ");
                        }
                        System.out.println("");
                    }
                    break;

                    
                case 4:
                    matrizResult =new int[filas][columnas];
                    System.out.println("\n");
                    for (int i = 0; i < matrizResult.length; i++) {
                        for (int j = 0; j < matrizResult.length; j++) {
                            matrizResult[i][j]= matrizA[i][j]-matrizB[i][j];
                        }
                    }
                    System.out.println("\n");
                    System.out.println("---- MATRIZ RESULTANTE ----");
                    for (int i = 0; i < matrizResult.length; i++) {
                        for (int j = 0; j < matrizResult.length; j++) {
                            System.out.println(" | " + matrizResult[i][j] + " | ");
                        }
                        System.out.println("");
                    }
                    break;
                    
                case 5:
                    matrizResult =new int[filas][columnas];
                    System.out.println("\n");
                    for (int i = 0; i < matrizResult.length; i++) {
                        for (int j = 0; j < matrizResult.length; j++) {
                            matrizResult[i][j] = 2 * matrizA[i][j];
                        }
                    }
                    System.out.println("\n");
                    System.out.println("---- MATRIZ A RESULTANTE ----");
                    for (int i = 0; i < matrizResult.length; i++) {
                        for (int j = 0; j < matrizResult.length; j++) {
                            System.out.println(" | " + matrizResult[i][j] + " | ");
                        }
                        System.out.println("");
                    }
                    System.out.println("\n");
                    for (int i = 0; i < matrizResult.length; i++) {
                        for (int j = 0; j < matrizResult.length; j++) {
                            matrizResult[i][j] = 2 * matrizB[i][j];
                        }
                    }
                    System.out.println("\n");
                    System.out.println("---- MATRIZ B RESULTANTE ----");
                    for (int i = 0; i < matrizResult.length; i++) {
                        for (int j = 0; j < matrizResult.length; j++) {
                            System.out.println(" | " + matrizResult[i][j] + " | ");
                        }
                        System.out.println("");
                    }
                    break;
                    
                case 6:
                    matrizResult =new int[filas][columnas];
                    System.out.println("\n");
                    for (int i = 0; i < matrizResult.length; i++) {
                        for (int j = 0; j < matrizResult.length; j++) {
                            matrizResult[i][j]= matrizA[i][j]*matrizB[i][j];
                        }
                    }
                    System.out.println("\n");
                    System.out.println("---- MATRIZ RESULTANTE ----");
                    for (int i = 0; i < matrizResult.length; i++) {
                        for (int j = 0; j < matrizResult.length; j++) {
                            System.out.println(" | " + matrizResult[i][j] + " | ");
                        }
                        System.out.println("");
                    }
                    break;
                    
                case 7:
                    int [][]matrizT =  new int[filas][columnas];
                    System.out.println("\n");
                    for (int i = 0; i < matrizT.length; i++) {
                        for (int j = 0; j <matrizT[i].length; j++) {
                            matrizT[j][i]= matrizA[i][j];
                        }
                    }
                    System.out.println("\n");
                    System.out.println("---- MATRIZ A RESULTANTE ----");
                    for (int i = 0; i < matrizT.length; i++) {
                        for (int j = 0; j < matrizT[i].length; j++) {
                            System.out.println(" | " + matrizT[i][j] + " | ");
                        }
                        System.out.println("");
                    }
                    System.out.println("\n");
                    for (int i = 0; i < matrizT.length; i++) {
                        for (int j = 0; j <matrizT[i].length; j++) {
                            matrizT[j][i]= matrizB[i][j];
                        }
                    }
                    System.out.println("\n");
                    System.out.println("---- MATRIZ B RESULTANTE ----");
                    for (int i = 0; i < matrizT.length; i++) {
                        for (int j = 0; j < matrizT[i].length; j++) {
                            System.out.println(" | " + matrizT[i][j] + " | ");
                        }
                        System.out.println("");
                    }
                    break;
                case 8:
                    System.out.println("Saliendo.....");
                default:
                    System.out.println("Error de Opcion!");
            }
            System.out.println("\n");
        } while (opc!=8);
    }
}
