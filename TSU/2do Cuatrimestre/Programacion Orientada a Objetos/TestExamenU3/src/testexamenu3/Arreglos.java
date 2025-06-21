/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testexamenu3;

/**
 *
 * @author clientepreferido
 */
public class Arreglos {

    private final String array[][];
    private int encontrado, siguienteVacio, normal, plus;

    
    public Arreglos(int normal, int plus) {
        this.normal = normal;
        this.plus = plus;
        array = new String[normal + plus][3];
    }

    public int getNormal() {
        return normal;
    }

    public int getEncontrado() {
        return encontrado;
    }

    public int getSiguienteVacio() {
        return siguienteVacio;
    }

    public int getPlus() {
        return plus;
    }

    public void setNormal(int normal) {
        this.normal = normal;
    }

    public void setPlus(int plus) {
        this.plus = plus;
    }
    
    public String getMatrizClave(int encontrado) {
        return array[encontrado][0];
    }

    public String getMatrizNombre(int encontrado) {
        return array[encontrado][1];
    }

    public int getMatrizSocios(int encontrado) {
        return Integer.parseInt(array[encontrado][2]);
    }

    public void setMatrizClave(String clave) {
        array[encontrado][0] = clave;
    }

    public void setMatrizNombre(String nombre) {
        array[encontrado][1] = nombre;
    }

    public void setMatrizSocios(int socio) {
        array[encontrado][2] = "" + socio;
    }

    
    public void registrar(String clave, String nombre, int socio) {
        array[siguienteVacio][0] = clave;
        array[siguienteVacio][1] = nombre;
        array[siguienteVacio][2] = "" + socio;
        siguienteVacio++;
        if (socio == 1) {
            normal--;
        } else {
            plus--;
        }
    }
    
    public int buscar(String clave) {
        for (int i = 0; i < siguienteVacio; i++) {
            if (clave.equals(this.array[i][0])) {
                encontrado = i;
                return i;
            }
        }
        return -1;
    }

    public int eliminado(String clave) {
        int i, aux = -1;
        aux = buscar(clave);
        String socio;
        
        if (aux != -1) {
            socio = array[aux][2];
            for (i = encontrado; i < siguienteVacio -1; i++) {
                array[i] = array[i + 1];
            }
            array[i] = new String[3];
            siguienteVacio--;
            if (socio.equals("1")) {
                normal++;
            } else {
                plus++;
            }
        }
        return aux;
    }

}
