
package ejemplo_abstract2_;

public class rectangulo extends Figura {
    
    private float altura;

    @Override
    public void llenar() {
        System.out.print("Ingresa el valor de la Base: ");
        this.setLado(teclado.nextFloat());
        System.out.print("Ingresa el valor de la Altura: ");
        this.setLadoB(teclado.nextFloat());
    }

    @Override
    public void perimetro() {
        this.setPerimetro((2*this.getLado())+(2*this.getLadoB()));
    }

    @Override
    public void area() {
        this.setArea(this.getLado()*this.getLadoB());
    }

    public void setLadoB(float ladoB) {
        this.altura = ladoB;
    }

    public float getLadoB() {
        return altura;
    }
    
}
