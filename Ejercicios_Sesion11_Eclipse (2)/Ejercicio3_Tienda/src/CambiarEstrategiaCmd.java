public class CambiarEstrategiaCmd implements Comando {
    private Carrito carrito;
    private EstrategiaDescuento nueva;
    private EstrategiaDescuento anterior;

    public CambiarEstrategiaCmd(Carrito carrito, EstrategiaDescuento nueva) {
        this.carrito = carrito;
        this.nueva = nueva;
    }
    public void ejecutar() {
        anterior = carrito.getEstrategia();
        carrito.cambiarEstrategia(nueva);
    }
    public void deshacer() { carrito.cambiarEstrategia(anterior); }
    public String descripcion() { return "Cambiar promoción a " + nueva.getNombre(); }
}
