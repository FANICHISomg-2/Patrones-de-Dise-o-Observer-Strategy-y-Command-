import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

// Si hay 2 o más productos iguales, esos productos tienen 30% de descuento

public class DescuentoPorcentual implements EstrategiaDescuento {

    public double calcularTotal(List<Producto> carrito) {

        Map<String, List<Producto>> grupos = new LinkedHashMap<>();

        for (Producto p : carrito) {

            String clave = p.getNombre().toLowerCase();

            if (!grupos.containsKey(clave)) {
                grupos.put(clave, new ArrayList<>());
            }

            grupos.get(clave).add(p);
        }

        double total = 0;

        for (List<Producto> grupo : grupos.values()) {

            double subtotal = 0;

            for (Producto p : grupo) {
                subtotal += p.getPrecio();
            }

            total += (grupo.size() >= 2) ? subtotal * 0.70 : subtotal;
        }

        return total;
    }

    public String getNombre() {
        return "Descuento porcentual (2 iguales: 30%)";
    }
}
