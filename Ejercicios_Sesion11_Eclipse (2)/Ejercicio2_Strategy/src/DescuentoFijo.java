import java.util.List;

// 10% sobre el total
public class DescuentoFijo implements EstrategiaDescuento {
    public double calcularTotal(List<Producto> carrito) {
        return new SinDescuento().calcularTotal(carrito) * 0.90;
    }
    public String getNombre() { return "Descuento fijo (10%)"; }
}
