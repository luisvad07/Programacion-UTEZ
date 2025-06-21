/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejemplointerface;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author Luis
 */
public interface I_Operaciones {
    Scanner teclado = new Scanner(System.in);
        Date f_operacion = new Date();
        ArrayList Siuuuu = new ArrayList();
        public void alta();
        public void baja();
        public void modificacion();
        public void consulta();
}
