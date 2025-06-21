package ejemplo_interface_2_;

import java.util.Iterator;
import java.util.Scanner;

public class Producto implements I_Operaciones{
    String codigo;
    String Nombre;
    float precio;
    int existencia;
    String descripcion;
    String busqueda;
    int choix;
    

    public Producto() {
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getExistencia() {
        return existencia;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getBusqueda() {
        return busqueda;
    }

    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda;
    }

    public void setChoix(int choix) {
        this.choix = choix;
    }

    public int getChoix() {
        return choix;
    }
    
    
    
    @Override
    public void alta() {
        this.teclado.useDelimiter("\n");
        System.out.print("Ingresa el codigo del producto: ");
        this.setCodigo(teclado.next());
        System.out.print("Ingresa el Nombre del producto: ");
        this.setNombre(teclado.next());
        System.out.print("Ingresa la Descripcion del producto: ");
        this.setDescripcion(teclado.next());
        System.out.print("Ingresa el Precio del producto: ");
        this.setPrecio(teclado.nextFloat());
        System.out.print("Ingresa las Existencias: ");
        this.setExistencia(teclado.nextInt());
        /*
        Agregar al arraylist
        */
        lista_Objetos.add(this);
    }

    @Override
    public void baja() {
            System.out.print("Ingresa el codigo que quieres buscar: ");
            this.setBusqueda(teclado.next());
            int existe = this.getBusqueda().indexOf(this.getCodigo());
            if (existe!= -1) {
		System.out.println("El Producto "+this.getNombre()+" está en la posicion del ArrayList " + existe);
                boolean change = confirmarCambio();
                if (change) {
                    lista_Objetos.remove(existe);
                    System.out.println("Se ha realizado exitosamente la Modificacion...");
                } else {
                    System.out.println("Cambios no guardados!...");
                }
            } else {
		System.out.println("El elemento no existe");
            } 
    }

    @Override
    public void modificacion() {
        System.out.print("Ingresa el codigo que quieres buscar: ");
        this.setBusqueda(teclado.next());
        int existe = this.getBusqueda().indexOf(this.getCodigo());
            if (existe!= -1) {
		System.out.println("El Producto "+this.getNombre()+" está en la posicion del ArrayList " + existe);
                        System.out.println("Selecciona la operacion que desea efectuar:");
                        System.out.println("1.- Modificar Nombre del producto\n"
                                + "2.- Modificar Descripcion del producto\n"
                                + "3.- Modificar Precio del producto\n"
                                + "4.- Modificar Existencia del producto\n"
                                + "5.- Salir");
                        System.out.print("Opcion: ");
                        this.setChoix(teclado.nextInt());
                        switch (this.getChoix()) {
                            case 1: //Modificar Nombre del producto
                                boolean change = confirmarCambio();
                                if (change) {
                                    System.out.print("Ingresa el nuevo nombre del producto:");
                                    this.setNombre(teclado.next());
                                    System.out.println("Se ha realizado exitosamente la Modificacion...");
                                } else {
                                    System.out.println("Cambios no guardados!...");
                                }
                                break;
                            case 2: //Modificar Descripcion del producto
                                change = confirmarCambio();
                                if (change) {
                                    System.out.print("Ingresa la nueva descripcion del producto:");
                                    this.setNombre(teclado.next());
                                    System.out.println("Se ha realizado exitosamente la Modificacion...");
                                } else {
                                    System.out.println("Cambios no guardados!...");
                                }
                                break;    
                            case 3: //Modificar Precio del producto
                                change = confirmarCambio();
                                if (change) {
                                    System.out.print("Ingresa el nuevo precio del producto:");
                                    this.setNombre(teclado.next());
                                    System.out.println("Se ha realizado exitosamente la Modificacion...");
                                } else {
                                    System.out.println("Cambios no guardados!...");
                                }
                                break;    
                            case 4: //Modificar Existencia del producto
                                change = confirmarCambio();
                                if (change) {
                                    System.out.print("Ingresa la nueva existencia del producto:");
                                    this.setNombre(teclado.next());
                                    System.out.println("Se ha realizado exitosamente la Modificacion...");
                                } else {
                                    System.out.println("Cambios no guardados!...");
                                }
                                break;      
                            case 5: //Regresar al menu principal
                                System.out.println("Regresando a menu principal...");
                                break;
                            default:
                                System.out.println("Error de Opcion del submenu!");
                                break;
                        }
            } else {
		System.out.println("El elemento no existe");
	}
    }
    
    @Override
    public void consulta() {
        System.out.print("Ingresa el codigo que quieres buscar: ");
        this.setBusqueda(teclado.next());
        int existe = this.getBusqueda().indexOf(this.getCodigo());
            if (existe!= -1) {
		System.out.println("El Producto "+this.getNombre()+" está en la posicion del ArrayList " + existe);
            } else {
		System.out.println("El elemento no existe");
	}
    }
    
    @Override
    public void imprimir() {
        Iterator i=lista_Objetos.iterator();
        while(i.hasNext()){
            System.out.println(i.next());
        }
    }

    @Override
    public String toString() {
        return "Producto{" + "codigo=" + codigo + ", Nombre=" + Nombre + ", precio=" + precio + ", existencia=" + existencia + ", descripcion=" + descripcion + '}';
    }

    public static boolean confirmarCambio() { //Metodo para confirmar el cambio de la modificacion de un campo
        int resp;
        Scanner siuu = new Scanner(System.in);
        System.out.println("¿Estas seguro de realizar el cambio?");
        System.out.println("1.- Si");
        System.out.println("2.- No");
        System.out.print("Respuesta: ");
        resp = siuu.nextInt();
        siuu.skip("\n");
        return resp == 1; //Si es Verdadero acepta el cambio, Si es Falso lo rechaza
    }

    
}
