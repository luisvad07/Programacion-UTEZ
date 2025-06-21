/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testtienda;

import java.util.Scanner;

/**
 *
 * @author clientepreferido
 */
public class TestTienda {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner leer = new Scanner(System.in);
        //Variables para al acceso
        String pass;
        boolean key = false;
        //Variables para el Submenu
        int opcion, opcionComprar, change;
        boolean salir = false, cambiarDato;
        //Variables publicos
        String nombre = "";
        double precio = 0.0;

        int existencia = 0;
        int stock = 0;

        double venta = 0, totalDia = 0, inventario = 0; //total

        ContraAccess acceso = new ContraAccess();

        Productos producto1, producto2, producto3;

        for (int i = 0; i < 3 && !key; i++) {

            System.out.println("Ingresa la contraseña: (intento " + (i + 1) + ") ");
            pass = leer.nextLine();

            key = acceso.verificarContra(pass);

            if (key) {
                key = true;
                System.out.println("CONTRASEÑA CORRECTA");
                System.out.println("\n");
                System.out.println("========================================================");
                System.out.println("               BIENVENIDO A TIENDA ALFI");
                System.out.println("========================================================");

                System.out.print("Nombre del producto 1: ");
                nombre = leer.next();
                System.out.print("Precio del producto 1: ");
                precio = leer.nextDouble();
                System.out.print("Existencia: ");
                existencia = leer.nextInt();
                producto1 = new Productos(nombre, precio, existencia);

                System.out.print("Nombre del producto 2: ");
                nombre = leer.next();
                System.out.print("Precio del producto 2: ");
                precio = leer.nextDouble();
                System.out.print("Existencia: ");
                existencia = leer.nextInt();
                producto2 = new Productos(nombre, precio, existencia);

                System.out.print("Nombre del producto 3: ");
                nombre = leer.next();
                System.out.print("Precio del producto 3: ");
                precio = leer.nextDouble();
                System.out.print("Existencia: ");
                existencia = leer.nextInt();
                producto3 = new Productos(nombre, precio, existencia);

                System.out.println();

                do {
                    System.out.println("-----MENU-----");
                    System.out.println("1.- Venta " + producto1.getNombre() + "\n"
                            + "2.- Venta " + producto2.getNombre() + "\n"
                            + "3.- Venta " + producto3.getNombre() + "\n"
                            + "4.- Total\n"
                            + "5.- Comprar\n"
                            + "6.- Cambiar Precio\n"
                            + "7.- Salir");
                    System.out.print("Selecciona la opcion que desea efectuar: ");
                    opcion = leer.nextInt();

                    switch (opcion) {
                        case 1:
                            System.out.print("¿Cuantos productos vendera?: ");
                            stock = leer.nextInt();
                            if (stock <= producto1.getExistencia()) {
                                producto1.disminuirExistencia(stock);
                                venta = stock * producto1.getPrecio();
                                System.out.println("Productos vendidos: " + stock);
                                System.out.println("Productos en Existencia: " + producto1.getExistencia());
                                System.out.println("Total de productos vendidos: " + venta);
                            } else {
                                System.out.println("ERROR..No hay en el Sistema");
                            }
                            break;
                        case 2:
                            System.out.print("¿Cuantos productos vendera?: ");
                            stock = leer.nextInt();
                            if (stock <= producto2.getExistencia()) {
                                producto2.disminuirExistencia(stock);
                                venta = stock * producto2.getPrecio();
                                System.out.println("Productos vendidos: " + stock);
                                System.out.println("Productos en Existencia: " + producto2.getExistencia());
                                System.out.println("Total de productos vendidos: " + venta);
                            } else {
                                System.out.println("ERROR..No hay en el Sistema");
                            }
                            break;
                        case 3:
                            System.out.print("¿Cuantos productos vendera?: ");
                            stock = leer.nextInt();
                            if (stock <= producto3.getExistencia()) {
                                producto3.disminuirExistencia(stock);
                                venta = stock * producto3.getPrecio();
                                System.out.println("Productos vendidos: " + stock);
                                System.out.println("Productos en Existencia: " + producto3.getExistencia());
                                System.out.println("Total de productos vendidos: " + venta);
                            } else {
                                System.out.println("ERROR..No hay en el Sistema");
                            }
                            break;
                        case 4:

                            System.out.println("---------TOTAL PRODUCTOS---------");
                            System.out.println("Producto 1: " + producto1.getNombre());
                            System.out.println("Precio: $" + producto1.getPrecio());
                            System.out.println("Existencia:: " + producto1.getExistencia());

                            System.out.println("Producto 2: " + producto2.getNombre());
                            System.out.println("Precio: $" + producto2.getPrecio());
                            System.out.println("Existencia: " + producto2.getExistencia());

                            System.out.println("Producto 3: " + producto3.getNombre());
                            System.out.println("Precio: $" + producto3.getPrecio());
                            System.out.println("Existencia: " + producto3.getExistencia());

                            inventario = (producto1.getPrecio() * producto1.getExistencia()) + (producto2.getPrecio() * producto2.getExistencia())
                                    + (producto3.getPrecio() * producto3.getExistencia());
                            System.out.println("El total por los productos es de: $" + inventario);
                            System.out.println("\n");
                            break;
                        case 5:
                            System.out.println("-----SUBMENU COMPRAR-----");
                            System.out.println("1.- " + producto1.getNombre() + "\n"
                                    + "2.- " + producto2.getNombre() + "\n"
                                    + "3.- " + producto3.getNombre() + "\n"
                                    + "4.- Regresar");
                            System.out.print("Selecciona la opcion que desea efectuar: ");
                            opcionComprar = leer.nextInt();
                            switch (opcionComprar) {
                                case 1:
                                    System.out.print("¿Cuantos productos comprara?: ");
                                    stock = leer.nextInt();
                                    if (stock <= producto1.getExistencia() || stock >= producto1.getExistencia()) {
                                        producto1.aumentarExistencia(stock);
                                        System.out.println("Productos Adquridos: " + stock);
                                        System.out.println("Productos en Existencia: " + producto1.getExistencia());
                                    } else {
                                        System.out.println("ERROR... Accion no exitosa!");
                                    }
                                    break;
                                case 2:
                                    System.out.print("¿Cuantos productos comprara?: ");
                                    stock = leer.nextInt();
                                    if (stock <= producto1.getExistencia() || stock >= producto1.getExistencia()) {
                                        producto1.aumentarExistencia(stock);
                                        System.out.println("Productos Adquridos: " + stock);
                                        System.out.println("Productos en Existencia: " + producto2.getExistencia());
                                    } else {
                                        System.out.println("ERROR... Accion no exitosa");
                                    }
                                    break;
                                case 3:
                                    System.out.print("¿Cuantos productos comprara?: ");
                                    stock = leer.nextInt();
                                    if (stock <= producto1.getExistencia() || stock >= producto1.getExistencia()) {
                                        producto1.aumentarExistencia(stock);
                                        System.out.println("Productos Adquridos: " + stock);
                                        System.out.println("Productos en Existencia: " + producto3.getExistencia());
                                    } else {
                                        System.out.println("ERROR... Accion no exitosa");
                                    }
                                    break;
                            }
                            break;
                        case 6:
                            System.out.println("-----SUBMENU CAMBIAR PRECIO------");
                            System.out.println("1.- " + producto1.getNombre() + "\n"
                                    + "2.- " + producto2.getNombre() + "\n"
                                    + "3.- " + producto3.getNombre() + "\n"
                                    + "4.- Regresar");
                            System.out.print("Selecciona la opcion que desea efectuar: ");
                            change = leer.nextInt();
                            switch (change) {
                                case 1:
                                    System.out.print("Ingresa el nuevo precio de " + producto1.getNombre() + " ");
                                    precio = leer.nextDouble();
                                    cambiarDato = changes();
                                    if (cambiarDato) {
                                        producto1.setPrecio(precio);
                                        System.out.println(precio);
                                        System.out.println("Se ha realizado exitosamente la Modificacion...");
                                    } else {
                                        System.out.println("Cambios no guardados!...");
                                    }
                                    break;
                                case 2:
                                    System.out.print("Ingresa el nuevo precio de " + producto2.getNombre() + " ");
                                    precio = leer.nextDouble();
                                    cambiarDato = changes();
                                    if (cambiarDato) {
                                        producto2.setPrecio(precio);
                                        System.out.println("Se ha realizado exitosamente la Modificacion...");
                                    } else {
                                        System.out.println("Cambios no guardados!...");
                                    }
                                    break;
                                case 3:
                                    System.out.print("Ingresa el nuevo precio de " + producto3.getNombre() + " ");
                                    precio = leer.nextDouble();
                                    cambiarDato = changes();
                                    if (cambiarDato) {
                                        producto3.setPrecio(precio);
                                        System.out.println("Se ha realizado exitosamente la Modificacion...");
                                    } else {
                                        System.out.println("Cambios no guardados!...");
                                    }
                                    break;
                            }
                            break;
                        case 7:
                            salir = true;
                            break;
                    }
                } while (!salir);
                System.out.println("\n");
                System.out.println("Saliendo...");

                totalDia = (producto1.getPrecio() * stock) + (producto2.getPrecio() * stock)
                        + (producto3.getPrecio() * stock);

                System.out.println("Ventas totales del dia: $" + totalDia);

            }
        }
        if (key == false) {
            System.out.println("ERROR...Ha superado el limite de intentos. Contraseña Incorrecta!");
        }
    }

    public static boolean changes() { //Metodo para confirmar el cambio de la modificacion del producto
        int answer;
        Scanner teclado = new Scanner(System.in);
        System.out.println("¿Estas seguro de realizar el cambio?");
        System.out.println("1.- Si");
        System.out.println("2.- No");
        answer = teclado.nextInt();
        teclado.skip("\n");
        return answer == 1; //Si es Verdadero acepta el cambio. Si es Falso, lo rechaza
    }
}
