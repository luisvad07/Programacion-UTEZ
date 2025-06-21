public class App {
    public static void main(String[] args) throws Exception {
        // Uso del Singleton de Configuración
        ConfiguracionSingleton configuracion = ConfiguracionSingleton.getInstancia();

        // Acceder y modificar la configuración
        System.out.println("Nombre de la aplicación: " + configuracion.getNombreApp());
        System.out.println("Versión de la aplicación: " + configuracion.getVersion());

        configuracion.setVersion(2.8);

        // Obtener otra instancia y verificar si es la misma
        ConfiguracionSingleton otraConfiguracion = ConfiguracionSingleton.getInstancia();

        System.out.println("¿Es la misma instancia? " + (configuracion == otraConfiguracion));
    }
}
