package client;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class clientRPC {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws MalformedURLException, XmlRpcException {
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setServerURL(new URL("http://localhost:1200"));
        XmlRpcClient client = new XmlRpcClient();
        client.setConfig(config);
        String option = "", firstNumber = "", secondNumber = "";
        Object response;
        do {
            System.out.println("1. Suma");
            System.out.println("2. Resta");
            System.out.println("3. Multiplicación");
            System.out.println("4. División");
            System.out.println("5. Exponente");
            System.out.println("6. Raíz");
            System.out.println("7. Consultar historial");
            System.out.println("8. Salir");
            System.out.println("Seleccione una opción...");
            option = sc.next();
            if (isNumber(option)) {
                switch (Integer.parseInt(option)) {
                    case 1:
                        System.out.println("SUMA");
                        do {
                            System.out.println("Ingrese el primer número");
                            firstNumber = sc.next();
                            if (!isDouble(firstNumber)) {
                                System.out.println("Ingrese un valor válido.");
                            }
                        } while (!isDouble(firstNumber));
                        do {
                            System.out.println("Ingrese el segundo número");
                            secondNumber = sc.next();
                            if (!isDouble(secondNumber)) {
                                System.out.println("Ingrese un valor válido.");
                            }
                        } while (!isDouble(secondNumber));
                        //Ejecución del método en el servidor.
                        Object[] data = {Double.parseDouble(firstNumber), Double.parseDouble(secondNumber)};
                        response = client.execute("Methods.suma", data);

                        System.out.println(response);
                        System.out.println(" ");
                        break;
                    case 2:
                        System.out.println("RESTA");
                        do {
                            System.out.println("Ingrese el primer número");
                            firstNumber = sc.next();
                            if (!isDouble(firstNumber)) {
                                System.out.println("Ingrese un valor válido.");
                            }
                        } while (!isDouble(firstNumber));
                        do {
                            System.out.println("Ingrese el segundo número");
                            secondNumber = sc.next();
                            if (!isDouble(secondNumber)) {
                                System.out.println("Ingrese un valor válido.");
                            }
                        } while (!isDouble(secondNumber));
                        //Ejecución del método en el servidor.
                        Object[] data2 = {Double.parseDouble(firstNumber), Double.parseDouble(secondNumber)};
                        response = client.execute("Methods.resta", data2);

                        System.out.println(response);
                        System.out.println(" ");
                        break;
                    case 3:
                        System.out.println("MULTIPLICACIÓN");
                        do {
                            System.out.println("Ingrese el primer número");
                            firstNumber = sc.next();
                            if (!isDouble(firstNumber)) {
                                System.out.println("Ingrese un valor válido.");
                            }
                        } while (!isDouble(firstNumber));
                        do {
                            System.out.println("Ingrese el segundo número");
                            secondNumber = sc.next();
                            if (!isDouble(secondNumber)) {
                                System.out.println("Ingrese un valor válido.");
                            }
                        } while (!isDouble(secondNumber));
                        //Ejecución del método en el servidor.
                        Object[] data3 = {Double.parseDouble(firstNumber), Double.parseDouble(secondNumber)};
                        response = client.execute("Methods.multiplicacion", data3);

                        System.out.println(response);
                        System.out.println(" ");
                        break;
                    case 4:
                        System.out.println("DIVISION");
                        do {
                            System.out.println("Ingrese el primer número");
                            firstNumber = sc.next();
                            if (!isDouble(firstNumber)) {
                                System.out.println("Ingrese un valor válido.");
                            }
                        } while (!isDouble(firstNumber));
                        do {
                            System.out.println("Ingrese el segundo número");
                            secondNumber = sc.next();
                            if (!isDouble(secondNumber)) {
                                System.out.println("Ingrese un valor válido.");
                            }
                        } while (!isDouble(secondNumber));
                        //Ejecución del método en el servidor.
                        Object[] data4 = {Double.parseDouble(firstNumber), Double.parseDouble(secondNumber)};
                        response = client.execute("Methods.division", data4);

                        System.out.println(response);
                        System.out.println(" ");
                        break;
                    case 5:
                        System.out.println("EXPONENTE");
                        do {
                            System.out.println("Ingrese el primer número");
                            firstNumber = sc.next();
                            if (!isDouble(firstNumber)) {
                                System.out.println("Ingrese un valor válido.");
                            }
                        } while (!isDouble(firstNumber));
                        do {
                            System.out.println("Ingrese el segundo número");
                            secondNumber = sc.next();
                            if (!isDouble(secondNumber)) {
                                System.out.println("Ingrese un valor válido.");
                            }
                        } while (!isDouble(secondNumber));
                        //Ejecución del método en el servidor.
                        Object[] data5 = {Double.parseDouble(firstNumber), Double.parseDouble(secondNumber)};
                        response = client.execute("Methods.exponente", data5);

                        System.out.println(response);
                        System.out.println(" ");
                        break;
                    case 6:
                        System.out.println("RAÍZ");
                        do {
                            System.out.println("Ingrese el primer número");
                            firstNumber = sc.next();
                            if (!isDouble(firstNumber)) {
                                System.out.println("Ingrese un valor válido.");
                            }
                        } while (!isDouble(firstNumber));
                        do {
                            System.out.println("Ingrese el segundo número");
                            secondNumber = sc.next();
                            if (!isDouble(secondNumber)) {
                                System.out.println("Ingrese un valor válido.");
                            }
                        } while (!isDouble(secondNumber));
                        //Ejecución del método en el servidor.
                        Object[] data6 = {Double.parseDouble(firstNumber), Double.parseDouble(secondNumber)};
                        response = client.execute("Methods.raiz", data6);

                        System.out.println(response);
                        System.out.println(" ");
                        break;
                    case 7:
                        System.out.println("HISTORIAL");
                        Object[] data7 = {"a"};
                        response = client.execute("Methods.history", data7);
                        System.out.println(response);
                        break;
                    case 8:
                        System.out.println("Adios");
                        break;
                    default:
                        System.err.println("No existe esa opción");
                }
            } else {
                System.out.println("La opción es incorrecta.\nIntente nuevamente\n");
            }
        } while (!option.equals("8"));
    }

    public static boolean isNumber(String number) {
        try {
            Integer.parseInt(number);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isDouble(String number) {
        try {
            Double.parseDouble(number);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
