
package ejemplo_abstract2_;

public class trapecio extends Figura {

    private float ladoB;
    private float ladoC;
    private float ladoD;
    private float altura;
    
    @Override
    public void llenar() {
        System.out.print("Ingresa el valor del Lado A (Base Mayor): "); //Base Mayor
        this.setLado(teclado.nextFloat());
        System.out.print("Ingresa el valor del Lado B(Base Menor): "); //Base Menor
        this.setLadoB(teclado.nextFloat());
        System.out.print("Ingresa el valor del Lado C: ");
        this.setLadoC(teclado.nextFloat());
        System.out.print("Ingresa el valor del Lado D: ");
        this.setLadoD(teclado.nextFloat());
        System.out.print("Ingresa el valor de la Altura: ");
        this.setAltura(teclado.nextFloat());
    }

    @Override
    public void perimetro() {
        this.setPerimetro(this.getLado()+this.getLadoB()+this.getLadoC()+this.getLadoD());
    }

    @Override
    public void area() {
        this.setArea(((this.getLado()+this.getLadoB())/2)*this.getAltura());
    }

    public float getLadoB() {
        return ladoB;
    }

    public void setLadoB(float ladoB) {
        this.ladoB = ladoB;
    }

    public float getLadoC() {
        return ladoC;
    }

    public void setLadoC(float ladoC) {
        this.ladoC = ladoC;
    }

    public float getLadoD() {
        return ladoD;
    }

    public void setLadoD(float ladoD) {
        this.ladoD = ladoD;
    }

    public float getAltura() {
        return altura;
    }

    public void setAltura(float altura) {
        this.altura = altura;
    }
    
    
}
