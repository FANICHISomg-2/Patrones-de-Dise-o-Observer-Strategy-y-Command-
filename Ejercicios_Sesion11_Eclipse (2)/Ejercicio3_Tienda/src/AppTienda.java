import java.util.Scanner;

public class AppTienda {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Producto[] catalogo = {
            new Producto("Mouse", 50),
            new Producto("Teclado", 120),
            new Producto("Audífonos", 80),
            new Producto("Monitor", 600)
        };

        EstrategiaDescuento[] estrategias = {
            new SinDescuento(),
            new DescuentoFijo(),
            new DescuentoPorcentual(),
            new DescuentoPorcentualAcumulado()
        };

        Tienda tienda = new Tienda();

        tienda.suscribir(new Cliente("Ana"));
        tienda.suscribir(new Cliente("Luis"));

        Carrito carrito = new Carrito(tienda);
        Historial historial = new Historial();

        int op;

        do {
            System.out.println("\n===== TIENDA EN LÍNEA =====");
            System.out.println("1. Agregar producto     2. Quitar producto");
            System.out.println("3. Cambiar promoción    4. Deshacer última acción");
            System.out.println("5. Ver carrito          6. Confirmar compra");
            System.out.println("7. Suscribir cliente    8. Desuscribir cliente");
            System.out.println("0. Salir");
            System.out.print("Opción: ");

            op = Integer.parseInt(sc.nextLine().trim());

            switch (op) {

                case 1:
                    for (int i = 0; i < catalogo.length; i++) {
                        System.out.println((i + 1) + ". " + catalogo[i]);
                    }

                    System.out.print("Producto: ");
                    int p = Integer.parseInt(sc.nextLine().trim());

                    historial.ejecutar(
                        new AgregarProductoCmd(carrito, catalogo[p - 1])
                    );

                    System.out.println("Agregado: " + catalogo[p - 1].getNombre());
                    break;

                case 2:
                    carrito.mostrar();

                    if (carrito.tamano() > 0) {
                        System.out.print("Posición a quitar: ");
                        int pos = Integer.parseInt(sc.nextLine().trim());

                        historial.ejecutar(
                            new QuitarProductoCmd(carrito, pos - 1)
                        );
                    }
                    break;

                case 3:
                    for (int i = 0; i < estrategias.length; i++) {
                        System.out.println(
                            (i + 1) + ". " + estrategias[i].getNombre()
                        );
                    }

                    System.out.print("Promoción: ");
                    int e = Integer.parseInt(sc.nextLine().trim());

                    historial.ejecutar(
                        new CambiarEstrategiaCmd(carrito, estrategias[e - 1])
                    );
                    break;

                case 4:
                    historial.deshacer();
                    break;

                case 5:
                    carrito.mostrar();
                    break;

                case 6:
                    carrito.confirmar();
                    historial.limpiar();
                    break;

                case 7:
                    System.out.print("Nombre del cliente: ");
                    String nuevo = sc.nextLine().trim();

                    tienda.suscribir(new Cliente(nuevo));

                    System.out.println(
                        nuevo + " fue suscrito. Suscritos: "
                        + tienda.totalSuscritos()
                    );
                    break;

                case 8:
                    System.out.print("Nombre del cliente: ");
                    String baja = sc.nextLine().trim();

                    System.out.println(
                        tienda.desuscribir(baja)
                        ? baja + " fue desuscrito. Suscritos: "
                            + tienda.totalSuscritos()
                        : "No existe ese cliente."
                    );
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
