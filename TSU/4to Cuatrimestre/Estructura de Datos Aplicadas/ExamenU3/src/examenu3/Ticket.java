/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examenu3;

/**
 *
 * @author Luis Valladolid
 */
public class Ticket {
    
    private double total_venta;
    private int productos_vendidos;

    public Ticket(double total_venta, int productos_vendidos) {
        this.total_venta = total_venta;
        this.productos_vendidos = productos_vendidos;
    }

    public double getTotal_venta() {
        return total_venta;
    }

    public void setTotal_venta(double total_venta) {
        this.total_venta = total_venta;
    }

    public int getProductos_vendidos() {
        return productos_vendidos;
    }

    public void setProductos_vendidos(int productos_vendidos) {
        this.productos_vendidos = productos_vendidos;
    }

    @Override
    public String toString() {
        return "Ticket{" + "total_venta=" + total_venta + ", productos_vendidos=" + productos_vendidos + '}';
    }
    
}
