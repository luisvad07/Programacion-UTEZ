package ejemplo_abstract2_;

import java.util.Scanner;

public abstract class Figura {

    private float lado;
    private float perimetro;
    private float area;
    Scanner teclado = new Scanner(System.in);

    public Figura() {
    }

    @Override
    public String toString() {
        return "Figura{" + "lado=" + lado + '}';
    }

    public float getLado() {
        return lado;
    }

    public void setLado(float lado) {
        this.lado = lado;
    }

    public float getPerimetro() {
        return perimetro;
    }

    public void setPerimetro(float perimetro) {
        this.perimetro = perimetro;
    }

    public float getArea() {
        return area;
    }

    public void setArea(float area) {
        this.area = area;
    }
    
    /*Metodos abstractos */
    public abstract void llenar();
    public abstract void perimetro();
    public abstract void area();
}
