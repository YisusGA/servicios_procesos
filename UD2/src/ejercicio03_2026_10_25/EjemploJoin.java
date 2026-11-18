package ejercicio03_2026_10_25;

public class EjemploJoin {
    static void main() throws InterruptedException {
        System.out.println("Inicio del programa (hilo principal)");

        Thread t1 = new Thread(new Tarea("Hilo-1"));
        Thread t2 = new Thread(new Tarea("Hilo-2"));
        Thread t3 = new Thread(new Tarea("Hilo-3"));

        t1.start();
        t2.start();
        t3.start();

        // Sincronización con join()
        System.out.println("Esperando a que terminen los hilos...");

        t1.join(); // el hilo principal espera a que termine Hilo-1
        t2.join(); // luego espera a Hilo-2
        t3.join(); // y finalmente a Hilo-3

        System.out.println("Todos los hilos han terminado.");
        System.out.println("Fin del programa (hilo principal)");
    }
}

class Tarea implements Runnable {

    private final String nombre;

    public Tarea(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void run() {
        System.out.println("Inicio de " + nombre);
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
