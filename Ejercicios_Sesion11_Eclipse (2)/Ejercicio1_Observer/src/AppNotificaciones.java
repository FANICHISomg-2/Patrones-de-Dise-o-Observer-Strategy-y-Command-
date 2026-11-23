import java.util.ArrayList;
import java.util.List;

class Notificacion {
    private String tipo;      // PROMOCIÓN o ACTUALIZACIÓN
    private String titulo;
    private String mensaje;

    public Notificacion(String tipo, String titulo, String mensaje) {
        this.tipo = tipo;
        this.titulo = titulo;
        this.mensaje = mensaje;
    }

    @Override
    public String toString() {
        return "[" + tipo + "] " + titulo + ": " + mensaje;
    }
}

interface Observador {
    void actualizar(Notificacion n);
}

class Usuario implements Observador {
    private String nombre;
    private List<Notificacion> bandeja = new ArrayList<>();

    public Usuario(String nombre) { this.nombre = nombre; }

    public String getNombre() { return nombre; }

    @Override
    public void actualizar(Notificacion n) {
        bandeja.add(n);
        System.out.println("   -> " + nombre + " recibió " + n);
    }

    public void suscribirse(CentroNotificaciones centro) {
        centro.suscribir(this);
        System.out.println(nombre + " se suscribió");
    }

    public void desuscribirse(CentroNotificaciones centro) {
        centro.desuscribir(this);
        System.out.println(nombre + " canceló su suscripción");
    }

    public int totalRecibidas() { return bandeja.size(); }
}

// Sujeto
class CentroNotificaciones {
    private List<Observador> suscriptores = new ArrayList<>();

    public void suscribir(Observador o) {
        if (!suscriptores.contains(o)) suscriptores.add(o);
    }

    public void desuscribir(Observador o) { suscriptores.remove(o); }

    public void publicar(Notificacion n) {
        System.out.println("Publicando: " + n);
        for (Observador o : suscriptores) {
            o.actualizar(n);
        }
    }
}

public class AppNotificaciones {
    public static void main(String[] args) {
        CentroNotificaciones centro = new CentroNotificaciones();
        Usuario ana = new Usuario("Ana");
        Usuario luis = new Usuario("Luis");
        Usuario marta = new Usuario("Marta");

        ana.suscribirse(centro);
        luis.suscribirse(centro);
        marta.suscribirse(centro);

        centro.publicar(new Notificacion("PROMOCIÓN", "Cyber Week", "30% de descuento en laptops"));

        luis.desuscribirse(centro);
        centro.publicar(new Notificacion("ACTUALIZACIÓN", "Producto nuevo", "Llegó el Mouse X200"));

        luis.suscribirse(centro);
        centro.publicar(new Notificacion("PROMOCIÓN", "2x1", "Audífonos en 2x1 hasta el domingo"));

        System.out.println();
        System.out.println("Notificaciones recibidas -> Ana: " + ana.totalRecibidas()
                + " | Luis: " + luis.totalRecibidas() + " | Marta: " + marta.totalRecibidas());
    }
}
