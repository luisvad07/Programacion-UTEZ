package ejemplo_abstract2_;

public class circulo extends Figura {
    
    private float radio;
    private final float pi = (float) 3.1416;

    @Override
    public void llenar() {
        System.out.print("Ingresa el valor del radio: ");
        this.setRadio(teclado.nextFloat());    
    }

    @Override
    public void perimetro() {
        this.setPerimetro(2*pi*this.getRadio());
    }

    @Override
    public void area() {
        this.setArea(pi*(this.getRadio()*this.getRadio()));
    }

    public float getRadio() {
        return radio;
    }

    public void setRadio(float radio) {
        this.radio = radio;
    }
    
}
