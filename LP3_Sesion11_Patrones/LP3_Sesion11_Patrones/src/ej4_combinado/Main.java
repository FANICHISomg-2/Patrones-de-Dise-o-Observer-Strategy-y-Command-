package ej4_combinado;

import java.util.*;

// ===== OBSERVER: los clientes reciben avisos de la tienda =====
interface Observador { void actualizar(String mensaje); }

class Cliente implements Observador {
    private final String nombre;
    Cliente(String nombre) { this.nombre = nombre; }
    public void actualizar(String mensaje) { System.out.println("   [" + nombre + "] " + mensaje); }
}

class Tienda {
    private final List<Observador> clientes = new ArrayList<>();
    void suscribir(Observador o) { clientes.add(o); }
    void desuscribir(Observador o) { clientes.remove(o); }
    void notificar(String mensaje) {
        System.out.println("Tienda notifica: " + mensaje);
        for (Observador o : clientes) o.actualizar(mensaje);
    }
}

// ===== STRATEGY: formas de calcular el precio =====
record Producto(String nombre, double precio) {}

interface EstrategiaPrecio { double aplicar(double subtotal); String nombre(); }

class SinDescuento implements EstrategiaPrecio {
    public double aplicar(double s) { return s; }
    public String nombre() { return "Sin descuento"; }
}

class DescuentoPorcentual implements EstrategiaPrecio {
    private final double pct;
    DescuentoPorcentual(double pct) { this.pct = pct; }
    public double aplicar(double s) { return s * (1 - pct / 100); }
    public String nombre() { return "Descuento " + (int) pct + "%"; }
}

class Carrito {   // contexto de Strategy
    private final List<Producto> items = new ArrayList<>();
    private EstrategiaPrecio estrategia = new SinDescuento();
    void agregar(Producto p) { items.add(p); System.out.println("Agregado al carrito: " + p.nombre()); }
    void setEstrategia(EstrategiaPrecio e) { this.estrategia = e; }
    double total() { return estrategia.aplicar(items.stream().mapToDouble(Producto::precio).sum()); }
}

// ===== COMMAND: acciones de la tienda como objetos =====
interface Comando { void ejecutar(); }

class AgregarProducto implements Comando {
    private final Carrito carrito; private final Producto producto;
    AgregarProducto(Carrito c, Producto p) { carrito = c; producto = p; }
    public void ejecutar() { carrito.agregar(producto); }
}

class CambiarPromocion implements Comando {
    private final Carrito carrito; private final Tienda tienda; private final EstrategiaPrecio estrategia;
    CambiarPromocion(Carrito c, Tienda t, EstrategiaPrecio e) { carrito = c; tienda = t; estrategia = e; }
    public void ejecutar() {
        carrito.setEstrategia(estrategia);                                    // Strategy
        tienda.notificar("Nueva promocion activa: " + estrategia.nombre());   // Observer
    }
}

class FinalizarCompra implements Comando {
    private final Carrito carrito; private final Tienda tienda;
    FinalizarCompra(Carrito c, Tienda t) { carrito = c; tienda = t; }
    public void ejecutar() {
        System.out.printf("Compra finalizada. Total a pagar: S/ %.2f%n", carrito.total());
        tienda.notificar("Se registro una compra en la tienda");
    }
}

class ProcesadorComandos {   // invoker con cola de comandos
    private final Queue<Comando> cola = new LinkedList<>();
    void encolar(Comando c) { cola.add(c); }
    void procesar() { while (!cola.isEmpty()) cola.poll().ejecutar(); }
}

public class Main {
    public static void main(String[] args) {
        Tienda tienda = new Tienda();
        Cliente ana = new Cliente("Ana"), luis = new Cliente("Luis");
        tienda.suscribir(ana); tienda.suscribir(luis);

        Carrito carrito = new Carrito();
        ProcesadorComandos proc = new ProcesadorComandos();
        proc.encolar(new AgregarProducto(carrito, new Producto("Laptop", 2500)));
        proc.encolar(new AgregarProducto(carrito, new Producto("Mouse", 50)));
        proc.encolar(new CambiarPromocion(carrito, tienda, new DescuentoPorcentual(10)));
        proc.encolar(new FinalizarCompra(carrito, tienda));
        proc.procesar();

        System.out.println("\n--- Luis se desuscribe ---");
        tienda.desuscribir(luis);
        proc.encolar(new CambiarPromocion(carrito, tienda, new DescuentoPorcentual(20)));
        proc.encolar(new FinalizarCompra(carrito, tienda));
        proc.procesar();
    }
}
