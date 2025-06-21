/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package testcajero;

/**
 *
 * @author eduardoA
 */
public class funcionesCajero {

    private double saldo = 5000;

    public funcionesCajero() {

    }

    public double retirar(double cantidadRetirar) {
        saldo = saldo - cantidadRetirar;
        return saldo;
    }

    public double depositar(double cantidadDepositar) {
        saldo = saldo + cantidadDepositar;
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public double getSaldo() {
        return saldo;
    }

}
