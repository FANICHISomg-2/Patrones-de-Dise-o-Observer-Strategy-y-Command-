// Interfaz Command con soporte para deshacer
public interface Comando {
    void ejecutar();
    void deshacer();
    String descripcion();
}
