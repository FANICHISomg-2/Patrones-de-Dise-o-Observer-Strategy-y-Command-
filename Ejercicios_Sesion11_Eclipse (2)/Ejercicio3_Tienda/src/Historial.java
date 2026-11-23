import java.util.ArrayDeque;
import java.util.Deque;

// Invoker: ejecuta comandos y guarda el historial para deshacer
public class Historial {
    private Deque<Comando> pila = new ArrayDeque<>();

    public void ejecutar(Comando c) {
        c.ejecutar();
        pila.push(c);
    }

    public void deshacer() {
        if (pila.isEmpty()) { System.out.println("No hay acciones para deshacer."); return; }
        Comando c = pila.pop();
        c.deshacer();
        System.out.println("Deshecho: " + c.descripcion());
    }

    public void limpiar() { pila.clear(); }
}
