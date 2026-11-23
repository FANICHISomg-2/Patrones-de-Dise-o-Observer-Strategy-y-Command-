import java.util.List;

// Desde 3 productos, 50% de descuento sobre el producto más barato
public class DescuentoPorcentualAcumulado implements EstrategiaDescuento {
    public double calcularTotal(List<Producto> carrito) {
        double total = new SinDescuento().calcularTotal(carrito);
        if (carrito.size() >= 3) {
            double menor = carrito.get(0).getPrecio();
            for (Producto p : carrito) menor = Math.min(menor, p.getPrecio());
            total -= menor * 0.50;
        }
        return total;
    }
    public String getNombre() { return "Descuento acumulado (3+ productos: 50% al más barato)"; }
}
