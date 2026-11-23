package ej3_command;

import java.util.ArrayDeque;
import java.util.Deque;

// Command
interface Comando { void ejecutar(); void deshacer(); }

// Receiver
class Televisor {
    private boolean encendido = false;
    private int volumen = 10, canal = 1;
    void alternarEnergia() { encendido = !encendido; System.out.println("TV " + (encendido ? "ENCENDIDO" : "APAGADO")); }
    boolean cambiarVolumen(int d) {
        if (!estaOn()) return false;
        volumen = Math.max(0, Math.min(100, volumen + d));
        System.out.println("Volumen: " + volumen); return true;
    }
    boolean cambiarCanal(int d) {
        if (!estaOn()) return false;
        canal = Math.max(1, canal + d);
        System.out.println("Canal: " + canal); return true;
    }
    private boolean estaOn() { if (!encendido) System.out.println("(El TV esta apagado)"); return encendido; }
}

// Concrete Commands
class Energia implements Comando {
    private final Televisor tv;
    Energia(Televisor tv) { this.tv = tv; }
    public void ejecutar() { tv.alternarEnergia(); }
    public void deshacer() { tv.alternarEnergia(); }
}

class Volumen implements Comando {
    private final Televisor tv; private final int delta; private boolean aplicado;
    Volumen(Televisor tv, int delta) { this.tv = tv; this.delta = delta; }
    public void ejecutar() { aplicado = tv.cambiarVolumen(delta); }
    public void deshacer() { if (aplicado) tv.cambiarVolumen(-delta); }
}

class Canal implements Comando {
    private final Televisor tv; private final int delta; private boolean aplicado;
    Canal(Televisor tv, int delta) { this.tv = tv; this.delta = delta; }
    public void ejecutar() { aplicado = tv.cambiarCanal(delta); }
    public void deshacer() { if (aplicado) tv.cambiarCanal(-delta); }
}

// Invoker
class ControlRemoto {
    private final String[] etiquetas = new String[5];
    private final Comando[] botones = new Comando[5];
    private final Deque<Comando> historial = new ArrayDeque<>();
    void asignar(int i, String etiqueta, Comando c) { etiquetas[i] = etiqueta; botones[i] = c; }
    void presionar(int i) {
        System.out.println("[Boton " + etiquetas[i] + "]");
        botones[i].ejecutar();
        historial.push(botones[i]);
    }
    void deshacer() {
        if (historial.isEmpty()) { System.out.println("Nada que deshacer"); return; }
        System.out.println("[Deshacer]");
        historial.pop().deshacer();
    }
}

public class Main {
    public static void main(String[] args) {
        Televisor tv = new Televisor();
        ControlRemoto control = new ControlRemoto();
        control.asignar(0, "ENCENDER/APAGAR", new Energia(tv));
        control.asignar(1, "VOLUMEN +", new Volumen(tv, +5));
        control.asignar(2, "VOLUMEN -", new Volumen(tv, -5));
        control.asignar(3, "CANAL +", new Canal(tv, +1));
        control.asignar(4, "CANAL -", new Canal(tv, -1));

        control.presionar(1);   // con el TV apagado no hace nada
        control.presionar(0);
        control.presionar(1);
        control.presionar(1);
        control.presionar(2);
        control.presionar(3);
        control.presionar(3);
        control.presionar(4);
        control.deshacer();
        control.deshacer();
        control.presionar(0);
    }
}
