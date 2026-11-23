import java.util.List;

public class CalculadoraDePrecios {
    private EstrategiaDescuento estrategia = new SinDescuento();

    public void setEstrategia(EstrategiaDescuento estrategia) {
        this.estrategia = estrategia;
    }

    public EstrategiaDescuento getEstrategia() { return estrategia; }

    public double calcularPrecioFinal(List<Producto> carrito) {
        return estrategia.calcularTotal(carrito);
    }
}
