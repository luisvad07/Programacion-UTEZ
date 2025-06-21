public class ConfiguracionSingleton {
    private static ConfiguracionSingleton instanciaUnica;
    
    // Variables de configuración de la aplicación
    private String nombreApp;
    private double version;

    // Constructor privado para evitar instanciación directa
    private ConfiguracionSingleton() {
        this.nombreApp = "AppRun";
        this.version = 2.8;
    }

    // Método para obtener la instancia única del Singleton
    public static ConfiguracionSingleton getInstancia() {
        if (instanciaUnica == null) {
            instanciaUnica = new ConfiguracionSingleton();
        }
        return instanciaUnica;
    }

    // Métodos para acceder y modificar la configuración (a traves de set y get)
    public String getNombreApp() {
        return nombreApp;
    }

    public void setNombreApp(String nombreApp) {
        this.nombreApp = nombreApp;
    }

    public double getVersion() {
        return version;
    }

    public void setVersion(double version) {
        this.version = version;
    }
}
