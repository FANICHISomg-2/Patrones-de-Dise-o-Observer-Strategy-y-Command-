package ej2_strategy;

import java.util.*;

class Producto {
    private final String nombre; private final double precio;
    Producto(String nombre, double precio) { this.nombre = nombre; this.precio = precio; }
    String getNombre() { return nombre; }
    double getPrecio() { return precio; }
    public String toString() { return nombre + " - S/ " + precio; }
}

// Strategy
interface EstrategiaDescuento {
    double calcular(List<Producto> carrito);
    String nombre();
}

class SinDescuento implements EstrategiaDescuento {
    public double calcular(List<Producto> c) { return CalculadoraDePrecios.suma(c); }
    public String nombre() { return "Sin descuento"; }
}

class DescuentoFijo implements EstrategiaDescuento {   // 10% sobre el total
    public double calcular(List<Producto> c) { return CalculadoraDePrecios.suma(c) * 0.90; }
    public String nombre() { return "Descuento fijo (10%)"; }
}

class DescuentoPorcentual implements EstrategiaDescuento {   // 30% a productos repetidos (2 o mas iguales)
    public double calcular(List<Producto> c) {
        Map<String, Integer> cant = new HashMap<>();
        for (Producto p : c) cant.merge(p.getNombre(), 1, Integer::sum);
        double total = 0;
        for (Producto p : c) total += cant.get(p.getNombre()) >= 2 ? p.getPrecio() * 0.70 : p.getPrecio();
        return total;
    }
    public String nombre() { return "Descuento porcentual (30% en 2 productos iguales)"; }
}

class DescuentoPorcentualAcumulado implements EstrategiaDescuento {   // 3 o mas: 50% al mas barato
    public double calcular(List<Producto> c) {
        double total = CalculadoraDePrecios.suma(c);
        if (c.size() >= 3) {
            double min = c.stream().mapToDouble(Producto::getPrecio).min().getAsDouble();
            total -= min * 0.50;
        }
        return total;
    }
    public String nombre() { return "Descuento acumulado (3+ productos: 50% al mas barato)"; }
}

// Contexto
class CalculadoraDePrecios {
    private EstrategiaDescuento estrategia = new SinDescuento();
    void setEstrategia(EstrategiaDescuento e) { this.estrategia = e; }
    double calcularTotal(List<Producto> carrito) { return estrategia.calcular(carrito); }
    static double suma(List<Producto> c) { return c.stream().mapToDouble(Producto::getPrecio).sum(); }
}

public class Main {
    static int leerInt(Scanner sc) {
        if (!sc.hasNextLine()) return 0;
        try { return Integer.parseInt(sc.nextLine().trim()); } catch (NumberFormatException e) { return -1; }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Producto> carrito = new ArrayList<>(List.of(new Producto("Mouse", 50), new Producto("Mouse", 50),
                new Producto("Teclado", 120), new Producto("Audifonos", 80)));
        EstrategiaDescuento[] estrategias = { new SinDescuento(), new DescuentoFijo(),
                new DescuentoPorcentual(), new DescuentoPorcentualAcumulado() };
        CalculadoraDePrecios calc = new CalculadoraDePrecios();
        int op;
        do {
            System.out.println("\n===== MENU DE DESCUENTOS =====");
            for (int i = 0; i < estrategias.length; i++) System.out.println((i + 1) + ". " + estrategias[i].nombre());
            System.out.println("5. Agregar producto\n6. Ver carrito\n0. Salir");
            System.out.print("Opcion: ");
            op = leerInt(sc);
            if (op >= 1 && op <= 4) {
                calc.setEstrategia(estrategias[op - 1]);   // se cambia la estrategia en tiempo de ejecucion
                System.out.printf("%s -> Total: S/ %.2f (sin descuento: S/ %.2f)%n",
                        estrategias[op - 1].nombre(), calc.calcularTotal(carrito), CalculadoraDePrecios.suma(carrito));
            } else if (op == 5) {
                try {
                    System.out.print("Nombre: "); String n = sc.nextLine();
                    System.out.print("Precio: "); double p = Double.parseDouble(sc.nextLine().trim());
                    carrito.add(new Producto(n, p));
                } catch (NumberFormatException e) { System.out.println("Precio invalido."); }
            } else if (op == 6) {
                carrito.forEach(System.out::println);
            }
        } while (op != 0);
    }
}
