
package ejemplo_abstract2_;

public class triangulo extends Figura {

    private float altura;
    private float lado_b;
    private float lado_c;
    private int tipo_triangulo;

    @Override
    public void llenar() {
        System.out.println("Ingresa la altura del triangulo");
        this.setAltura(teclado.nextFloat());
        System.out.println("Tipo de triangulo");
        System.out.println("1.Equilatero");
        System.out.println("2.Isoseles");
        System.out.println("3.Escaleno");
        System.out.print("Tipo: ");
        this.setTipo_triangulo(teclado.nextInt());
        System.out.print("Ingresa el valor del lado A: ");
        this.setLado(teclado.nextFloat());
        if (this.getTipo_triangulo() >= 2) {
            System.out.print("Ingresa el valor del lado B: ");
            this.setLado_b(teclado.nextFloat());
            if (this.getTipo_triangulo() == 3) {
                System.out.print("ngresa el valor del lado C: ");
                this.setLado_c(teclado.nextFloat());
            }
        }
    }

    @Override
    public void perimetro() {
        switch (this.getTipo_triangulo()) {
            case 1:
                /*Equilateros*/
                this.setPerimetro(this.getLado() * 3);
                break;
            case 2:
                /*Isoseles*/
                this.setPerimetro(this.getLado() + (2 * this.getLado_b()));
                break;
            case 3:
                /*Escaleno*/
                this.setPerimetro(this.getLado() + this.getLado_b() + this.getLado_c());
                break;
        }
    }

    @Override
    public void area() {
       this.setArea((this.getLado()*this.getAltura())/2);
    }

    public float getAltura() {
        return altura;
    }

    public void setAltura(float altura) {
        this.altura = altura;
    }

    public int getTipo_triangulo() {
        return tipo_triangulo;
    }

    public void setTipo_triangulo(int tipo_triangulo) {
        this.tipo_triangulo = tipo_triangulo;
    }

    public float getLado_b() {
        return lado_b;
    }

    public void setLado_b(float lado_b) {
        this.lado_b = lado_b;
    }

    public float getLado_c() {
        return lado_c;
    }

    public void setLado_c(float lado_c) {
        this.lado_c = lado_c;
    }

}
