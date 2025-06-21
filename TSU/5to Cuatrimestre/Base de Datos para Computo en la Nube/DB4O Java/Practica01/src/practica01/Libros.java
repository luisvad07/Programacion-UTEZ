/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica01;

/**
 *
 * @author Luis Valladolid
 */
public class Libros {
    
    private String Titulo;
    private String Autor;
    private int Año_Publicación;

    public Libros(String Titulo, String Autor, int Año_Publicación) {
        this.Titulo = Titulo;
        this.Autor = Autor;
        this.Año_Publicación = Año_Publicación;
    }

    public Libros() {
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String Titulo) {
        this.Titulo = Titulo;
    }

    public String getAutor() {
        return Autor;
    }

    public void setAutor(String Autor) {
        this.Autor = Autor;
    }

    public int getAño_Publicación() {
        return Año_Publicación;
    }

    public void setAño_Publicación(int Año_Publicación) {
        this.Año_Publicación = Año_Publicación;
    }

    @Override
    public String toString() {
        return "Libros{" + "Titulo=" + Titulo + ", Autor=" + Autor + ", Año_Publicación=" + Año_Publicación + '}';
    }

}
