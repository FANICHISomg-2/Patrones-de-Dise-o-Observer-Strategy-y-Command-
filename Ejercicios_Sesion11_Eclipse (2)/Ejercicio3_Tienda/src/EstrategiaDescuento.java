import java.util.List;

public interface EstrategiaDescuento {
    double calcularTotal(List<Producto> carrito);
    String getNombre();
}
