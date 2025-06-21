package utez.edu.mx.foodster.utils;

public class EventoEstados {

    public static final String EN_PROCESO = "En proceso";
    public static final String FINALIZADO = "Finalizado";

    public static final String CANCELADO = "Cancelado";
    public static final String PENDIENTE_PERSONAL = "Pendiente personal";
    public static final String PENDIENTE_PAGO = "Pendiente pago";

    private EventoEstados() {
        throw new UnsupportedOperationException("Esta clase no se puede instanciar");
    }
}
