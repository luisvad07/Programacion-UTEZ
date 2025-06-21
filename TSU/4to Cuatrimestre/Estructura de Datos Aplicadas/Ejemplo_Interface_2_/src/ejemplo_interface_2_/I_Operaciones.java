package ejemplo_interface_2_;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public interface I_Operaciones {
    Scanner teclado=new Scanner(System.in);
    Date f_operacion=new Date();
    ArrayList lista_Objetos=new ArrayList();
    
    public void alta();
    public void baja();
    public void modificacion();
    public void consulta();
    public void imprimir();
    
}
