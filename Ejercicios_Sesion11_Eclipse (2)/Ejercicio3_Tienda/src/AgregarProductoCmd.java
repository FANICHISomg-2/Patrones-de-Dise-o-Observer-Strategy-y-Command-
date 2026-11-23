public class AgregarProductoCmd implements Comando {
    private Carrito carrito;
    private Producto producto;

    public AgregarProductoCmd(Carrito carrito, Producto producto) {
        this.carrito = carrito;
        this.producto = producto;
    }
    public void ejecutar() { carrito.agregar(producto); }
    public void deshacer() { carrito.quitar(carrito.tamano() - 1); }
    public String descripcion() { return "Agregar " + producto.getNombre(); }
}
