public class QuitarProductoCmd implements Comando {
    private Carrito carrito;
    private int posicion;
    private Producto quitado;

    public QuitarProductoCmd(Carrito carrito, int posicion) {
        this.carrito = carrito;
        this.posicion = posicion;
    }
    public void ejecutar() { quitado = carrito.quitar(posicion); }
    public void deshacer() { carrito.agregar(posicion, quitado); }
    public String descripcion() { return "Quitar " + quitado.getNombre(); }
}
