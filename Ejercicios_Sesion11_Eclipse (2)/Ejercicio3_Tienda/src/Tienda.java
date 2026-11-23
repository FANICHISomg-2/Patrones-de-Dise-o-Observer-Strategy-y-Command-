import java.util.ArrayList;
import java.util.List;

// Sujeto (Observer)
public class Tienda {
    private List<Cliente> clientes = new ArrayList<>();

    public void suscribir(Cliente c) { clientes.add(c); }

    public boolean desuscribir(String nombre) {
        return clientes.removeIf(c -> c.getNombre().equalsIgnoreCase(nombre));
    }

    public void notificar(String evento) {
        for (Cliente c : clientes) c.actualizar(evento);
    }

    public int totalSuscritos() { return clientes.size(); }
}
