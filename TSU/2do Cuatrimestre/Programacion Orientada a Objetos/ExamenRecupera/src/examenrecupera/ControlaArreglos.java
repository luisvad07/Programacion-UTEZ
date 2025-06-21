/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examenrecupera;

/**
 *
 * @author clientepreferido
 */
public class ControlaArreglos {

    private final String arregloPrincipal[][];
    private int encontrado, ap, programacion, matematicas;
    

    public ControlaArreglos(int programacion, int matematicas) {
        this.programacion = programacion;
        this.matematicas = matematicas;
        arregloPrincipal = new String[programacion + matematicas][3];
    }

    public int getProgramacion() {
        return programacion;
    }

    public int getMatematicas() {
        return matematicas;
    }

    public int getEncontrado() {
        return encontrado;
    }

    public int getSiguienteVacio() {
        return ap;
    }
    
    public void setProgramacion(int prog1) {
        this.programacion = prog1;
    }

    public void setMatematicas(int mate1) {
        this.matematicas = mate1;
    }

    public String getClave(int encontrado) {
        return arregloPrincipal[encontrado][0];
    }

    public String getNombre(int encontrado) {
        return arregloPrincipal[encontrado][1];
    }

    public int getCurso(int encontrado) {
        return Integer.parseInt(arregloPrincipal[encontrado][2]);
    }

    public void setClave(String clave) {
        arregloPrincipal[encontrado][0] = clave;
    }

    public void setNombre(String nombre) {
        arregloPrincipal[encontrado][1] = nombre;
    }

    public void setCurso(int curso) {
        arregloPrincipal[encontrado][2] = "" + curso;
    }
    
    public void disminuirSiguienteVacio() {
        ap--;
    }
    

    public void registrar(String clave, String nombre, int curso) {
        arregloPrincipal[ap][0] = clave;
        arregloPrincipal[ap][1] = nombre;
        arregloPrincipal[ap][2] = "" + curso;
        ap++;
        if (curso == 1) {
            programacion--;
        }
        
        else {
            matematicas--;
        }
    }

    public int busqueda(String clave) {
        int busq = -1;
        for (int i = 0; i < ap; i++) {
            if (clave.equals(this.arregloPrincipal[i][0])) {
                busq = i;
            }
        }
        return busq;
    }

    public int baja(String clave) {
        int i, aux = -1;
        aux = busqueda(clave);
        String curso;

        if (aux != -1) {
            curso = arregloPrincipal[aux][2];
            for (i = encontrado; i < ap - 1; i++) {
                arregloPrincipal[i] = arregloPrincipal[i + 1];
            }
            arregloPrincipal[i] = new String[3];
            ap--;
            if (curso.equals("1")) {
                programacion++;
            }
            if (curso.equals("2")) {
                matematicas++;
            }
        }
        return aux;
    }
}
