import java.util.ArrayList;
import java.util.List;

// Receptor del patrón Command; contexto del patrón Strategy
public class Carrito {
    private List<Producto> items = new ArrayList<>();
    private EstrategiaDescuento estrategia = new SinDescuento();
    private Tienda tienda;

    public Carrito(Tienda tienda) { this.tienda = tienda; }

    public void agregar(Producto p) { items.add(p); }
    public void agregar(int pos, Producto p) { items.add(pos, p); }
    public Producto quitar(int pos) { return items.remove(pos); }
    public int tamano() { return items.size(); }

    public EstrategiaDescuento getEstrategia() { return estrategia; }

    public void cambiarEstrategia(EstrategiaDescuento nueva) {
        this.estrategia = nueva;
        tienda.notificar("Promoción activa: " + nueva.getNombre());   // Observer
    }

    public double subtotal() { return new SinDescuento().calcularTotal(items); }
    public double total() { return estrategia.calcularTotal(items); }   // Strategy

    public void mostrar() {
        if (items.isEmpty()) { System.out.println("Carrito vacío."); return; }
        for (int i = 0; i < items.size(); i++)
            System.out.println((i + 1) + ". " + items.get(i));
        System.out.printf("Subtotal: S/ %.2f%n", subtotal());
        System.out.println("Promoción: " + estrategia.getNombre());
        System.out.printf("Total a pagar: S/ %.2f%n", total());
    }

    public void confirmar() {
        if (items.isEmpty()) { System.out.println("No hay productos para confirmar."); return; }
        double total = total();
        items.clear();
        tienda.notificar(String.format(java.util.Locale.US, "Pedido confirmado por S/ %.2f", total));
    }
}
