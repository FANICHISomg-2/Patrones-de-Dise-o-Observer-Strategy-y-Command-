import java.util.List;

public class SinDescuento implements EstrategiaDescuento {
    public double calcularTotal(List<Producto> carrito) {
        double total = 0;
        for (Producto p : carrito) total += p.getPrecio();
        return total;
    }
    public String getNombre() { return "Sin descuento"; }
}
