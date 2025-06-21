/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testarreglodeobjetos;

/**
 *
 * @author clientepreferido
 */
public class Arreglo {

    private Persona[] arrayP;
    private int next;
    private int auxt;


    public Persona getPersona(int longitud) {
        return arrayP[longitud];
    }
    
    public Arreglo(int tam) {
        arrayP = new Persona[tam];
    }

    public Persona[] getPersonas() {
        return arrayP;
    }

    public void setArrayP(Persona[] arrayP) {
        this.arrayP = arrayP;
    }
    
    public void setNext(int next) {
        this.next = next;
    }

    public int getNext() {
        return next;
    }

    public Arreglo(String clave, String nombre, int edad, double estatura) {
        //arrayP[next] = new Arreglo(clave, nombre, edad, estatura);
        arrayP[next].setClave(clave);
        arrayP[next].setNombre(nombre);
        arrayP[next].setEdad(edad);
        arrayP[next].setEstatura(estatura);
        next++;
    }
    
    
    public boolean hayEspacio() {
        return next < arrayP.length;
    }

    public int buscar(String clave) {
        int aux = -1;
        for (int i = 0; i < next; i++) {
            if (this.arrayP[i].getClave().equals(clave)) {
                aux = i;
                this.auxt = i;
            }
        }
        return aux;
    }

    public void eliminar(int eliminar) {
        for (int i = 0; i < next - 1; i++) {
            arrayP[auxt] = arrayP[i + 1];
        }
        next--;
    }

    public int longitud() {
        return this.arrayP.length;

    }

    public void imprimirPersonas(int i) {
        arrayP[i].getClave();
        arrayP[i].getNombre();
        arrayP[i].getEdad();
        arrayP[i].getEstatura();
    }
    
    public void incrementarEspacio() {
        next++;
    }
    
}
