package ej1_observer;

import java.util.ArrayList;
import java.util.List;

interface Observador { void actualizar(Notificacion n); }

class Notificacion {
    private final String tipo, mensaje;
    Notificacion(String tipo, String mensaje) { this.tipo = tipo; this.mensaje = mensaje; }
    String getTipo() { return tipo; }
    String getMensaje() { return mensaje; }
}

class Usuario implements Observador {
    private final String nombre;
    Usuario(String nombre) { this.nombre = nombre; }
    String getNombre() { return nombre; }
    public void actualizar(Notificacion n) {
        System.out.println("   -> " + nombre + " recibio [" + n.getTipo() + "]: " + n.getMensaje());
    }
}

// Subject: el centro de notificaciones mantiene la lista de suscriptores
class CentroNotificaciones {
    private final List<Observador> suscriptores = new ArrayList<>();
    void suscribir(Observador o) { if (!suscriptores.contains(o)) suscriptores.add(o); }
    void desuscribir(Observador o) { suscriptores.remove(o); }
    void publicar(Notificacion n) {
        System.out.println("Publicando " + n.getTipo() + " a " + suscriptores.size() + " suscriptor(es)");
        for (Observador o : suscriptores) o.actualizar(n);
    }
}

public class Main {
    public static void main(String[] args) {
        CentroNotificaciones centro = new CentroNotificaciones();
        Usuario ana = new Usuario("Ana"), luis = new Usuario("Luis"), rosa = new Usuario("Rosa");
        centro.suscribir(ana); centro.suscribir(luis); centro.suscribir(rosa);
        centro.publicar(new Notificacion("PROMOCION", "2x1 en laptops este fin de semana"));

        System.out.println("\nLuis se desuscribe");
        centro.desuscribir(luis);
        centro.publicar(new Notificacion("ACTUALIZACION", "Nuevo stock de teclados mecanicos"));

        System.out.println("\nLuis vuelve a suscribirse");
        centro.suscribir(luis);
        centro.publicar(new Notificacion("PROMOCION", "20% de descuento en accesorios"));
    }
}