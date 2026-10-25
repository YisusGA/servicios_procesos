package ejercicio03_2026_10_25;

public class Ejercicio03 {
    static void main() throws InterruptedException {
        System.out.println("Inicio del programa (hilo principal)");

        Thread t0 = new Thread(new TareaEncadenada("Thread-0", null));
        Thread t1 = new Thread(new TareaEncadenada("Thread-1", t0));
        Thread t2 = new Thread(new TareaEncadenada("Thread-2", t1));

        t0.start();
        t1.start();
        t2.start();

        t2.join();
        System.out.println("Todos los hilos han terminado.");
        System.out.println("Fin del programa (hilo principal)");
    }
}

class TareaEncadenada implements Runnable {

    private final String nombre;
    private Thread hiloAnterior;

    public TareaEncadenada(String nombre, Thread hiloAnterior) {
        this.nombre = nombre; this.hiloAnterior = hiloAnterior;
    }

    @Override
    public void run() {
        System.out.println("Inicio de " + nombre);
        if (hiloAnterior != null) {
            System.out.println("Me espero a que termine " + hiloAnterior.getName());
            try {
                hiloAnterior.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        for (int i = 1; i <= 5; i++) {
            System.out.println(nombre + " ejecutando paso " + i);
            try {
                Thread.sleep(500); // simula trabajo
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Fin de " + nombre);
    }
}
