public class Cliente implements Observador {
    private String nombre;

    public Cliente(String nombre) { this.nombre = nombre; }

    public String getNombre() { return nombre; }

    @Override
    public void actualizar(String evento) {
        System.out.println("   [Notificación para " + nombre + "] " + evento);
    }
}
