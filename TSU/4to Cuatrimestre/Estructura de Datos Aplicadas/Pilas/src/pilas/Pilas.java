/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pilas;

import java.util.Scanner;

/**
 *
 * @author Luis Valladolid
 */
public class Pilas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        PilaEnlazada pilaAlumnos=new PilaEnlazada();
        int opc;
        //insertar alumnos hasta que opc!=1
        do{
            //Objeto alumno
            Alumno nuevoAlumno=new Alumno();
            System.out.println("Escribe el Nombre");
            nuevoAlumno.setNombre(s.next());
            System.out.println("Escribe la edad");
            nuevoAlumno.setEdad(s.nextInt());
            int opc2;
            int i=1;
            do{
                System.out.print("Insertar Calificacion "+i+": ");
                nuevoAlumno.getPilaCalificaciones().insertar(s.nextFloat());
                System.out.println("Si desea otra calificacion");
                System.out.println("1.Si");
                System.out.println("2.No");
                opc2=s.nextInt();
                i++;
            }while(opc2==1);
            
            pilaAlumnos.insertar(nuevoAlumno);
            
            System.out.println("¿Quieres agregar otro ALumno?");
            System.out.println("1.Si");
            System.out.println("0.No");
            opc=s.nextInt();
            
        }while(opc==1);
        pilaAlumnos.imprimir();
    }
    
}
