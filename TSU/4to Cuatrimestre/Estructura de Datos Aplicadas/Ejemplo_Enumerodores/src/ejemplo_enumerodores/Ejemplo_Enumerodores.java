package ejemplo_enumerodores;

import java.util.*;

public class Ejemplo_Enumerodores {

    enum tipo_vehiculo {
        AUTOS, MOTOS, CAMIONES;
    }

    enum opcion_crud {
        ALTA, BAJA, CONSULTA, MODIFICACION;
    }
    
    static Scanner s = new Scanner(System.in);

    public static void main(String[] args) {
        ArrayList vehiculos = new ArrayList();
        int continuar = 0;
        do {
            Vehiculo vehiculo = null;
            switch (seleccionarTV()) {
                case AUTOS:
                    vehiculo = new Auto();
                    break;
                case MOTOS:
                    break;
                case CAMIONES:
                    break;
                default:
                    System.out.println("Opcion No Valida!");
            }
            opcion_crud op_crud = seleccionarCrud();
            switch (op_crud) {
                case ALTA:
                    vehiculo.alta();
                    vehiculos.add(vehiculo);
                    break;
                case BAJA:
                    vehiculo.baja();
                    break;
                case CONSULTA:
                    //imprimirLista(vehiculos);
                    vehiculo.consulta();
                    break;
                case MODIFICACION:
                    vehiculo.modificacion();
                    break;    
                default:
                    System.out.println("Opcion No Valida!");
            }
            System.out.println("¿Desea Continuar?");
            System.out.println("1.Si");
            System.out.println("0.No");
            System.out.print("Opcion: ");
            continuar = s.nextInt();
            s.nextLine();
        } while (continuar == 1);
    }

    public static tipo_vehiculo seleccionarTV() {
        tipo_vehiculo tv = null;
        System.out.println("Escriba el tipo de vehiculo que desea administrar:");
        System.out.println("AUTOS");
        System.out.println("MOTOS");
        System.out.println("CAMIONES");
        System.out.print("Opcion: ");
        try {
            tv = tipo_vehiculo.valueOf(s.nextLine().toUpperCase());
        } catch (Exception e) {
            System.err.println("Algo salio mal, intentando de nuevo");
            seleccionarTV();
        }
        return tv;
    }

    public static opcion_crud seleccionarCrud() {
        opcion_crud opc = null;
        System.out.println("Que operación va a realizar:");
        System.out.println("ALTA");
        System.out.println("BAJA");
        System.out.println("CONSULTA");
        System.out.println("MODIFICACION");
        System.out.print("Opcion: ");
        try {
            opc = opcion_crud.valueOf(s.nextLine().toUpperCase());
        } catch (Exception e) {
            System.err.println("Algo salio mal, intentando de nuevo");
            seleccionarTV();
        }
        return opc;
    }

    public static void imprimirLista(ArrayList<Vehiculo> vehiculos) {
        for (Iterator<Vehiculo> iterator = vehiculos.iterator(); iterator.hasNext();) {
            Vehiculo next = iterator.next();
            System.out.println(next.toString());
        }
    }
}
