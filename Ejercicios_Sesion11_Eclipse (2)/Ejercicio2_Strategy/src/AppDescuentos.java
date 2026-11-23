import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AppDescuentos {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        List<Producto> carrito = new ArrayList<>();
        CalculadoraDePrecios calc = new CalculadoraDePrecios();

        EstrategiaDescuento[] estrategias = {
            new SinDescuento(),
            new DescuentoFijo(),
            new DescuentoPorcentual(),
            new DescuentoPorcentualAcumulado()
        };

        int op;

        do {
            System.out.println("\n===== MENÚ DE DESCUENTOS =====");
            System.out.println("1. Agregar producto al carrito");
            System.out.println("2. Ver carrito");
            System.out.println("3. Elegir estrategia de descuento");
            System.out.println("4. Calcular precio final");
            System.out.println("0. Salir");
            System.out.print("Opción: ");

            op = Integer.parseInt(sc.nextLine().trim());

            switch (op) {

                case 1:
                    System.out.print("Nombre: ");
                    String nombre = sc.nextLine().trim();

                    System.out.print("Precio: ");
                    double precio = Double.parseDouble(sc.nextLine().trim());

                    carrito.add(new Producto(nombre, precio));
                    System.out.println("Producto agregado.");
                    break;

                case 2:
                    if (carrito.isEmpty()) {
                        System.out.println("Carrito vacío.");
                    }

                    for (int i = 0; i < carrito.size(); i++) {
                        System.out.println((i + 1) + ". " + carrito.get(i));
                    }
                    break;

                case 3:
                    for (int i = 0; i < estrategias.length; i++) {
                        System.out.println((i + 1) + ". " + estrategias[i].getNombre());
                    }

                    System.out.print("Estrategia: ");
                    int e = Integer.parseInt(sc.nextLine().trim());

                    if (e >= 1 && e <= estrategias.length) {
                        calc.setEstrategia(estrategias[e - 1]);

                        System.out.println("Estrategia activa: "
                                + calc.getEstrategia().getNombre());
                    } else {
                        System.out.println("Opción inválida.");
                    }
                    break;

                case 4:
                    double sinDesc = new SinDescuento().calcularTotal(carrito);
                    double fin = calc.calcularPrecioFinal(carrito);

                    System.out.printf("Subtotal: S/ %.2f%n", sinDesc);
                    System.out.printf("Estrategia: %s%n",
                            calc.getEstrategia().getNombre());
                    System.out.printf("Precio final: S/ %.2f (ahorro S/ %.2f)%n",
                            fin, sinDesc - fin);
                    break;

                case 0:
                    System.out.println("Hasta luego.");
                    break;

                default:
                    System.out.println("Opción inválida.");
            }

        } while (op != 0);

        sc.close();
    }
}
