package ejemplo_abstract2_;

public class rombo extends Figura {

    private float diamMayor;
    private float diamMenor;

    @Override
    public void llenar() {
        System.out.print("Ingresa el valor de uno de sus lados: ");
        this.setLado(teclado.nextFloat());
        System.out.print("Ingresa el valor del Diametro Mayor: ");
        this.setDiamMayor(teclado.nextFloat());
        System.out.print("Ingresa el valor del Diametro Menor: ");
        this.setDiamMenor(teclado.nextFloat());
    }

    @Override
    public void perimetro() {
        this.setPerimetro(4*this.getLado());
    }

    @Override
    public void area() {
        this.setArea((this.getDiamMayor()*this.getDiamMenor())/2);
    }

    public float getDiamMayor() {
        return diamMayor;
    }

    public void setDiamMayor(float diamMayor) {
        this.diamMayor = diamMayor;
    }

    public float getDiamMenor() {
        return diamMenor;
    }

    public void setDiamMenor(float diamMenor) {
        this.diamMenor = diamMenor;
    }

    
}
