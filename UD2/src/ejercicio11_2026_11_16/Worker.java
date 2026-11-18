package ejercicio11_2026_11_16;

import java.util.Map;
import java.util.concurrent.Semaphore;

public class Worker extends Thread {
    private int numero;
    private Semaphore semaforo;
    // El value del Boolean es false si no está disponible y true si lo está
    private Map<Worker, Boolean> workers;

    public Worker(Semaphore semaforo, Map<Worker, Boolean> workers) {
        this.semaforo = semaforo;
        this.workers = workers;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(50000);
            } catch (InterruptedException e) {
                System.out.printf("Soy hilo Worker con ID %s. Mi variable vale: %d\n", threadId(), numero * 1000);
                // Este bloque synchronized realmente no es necesario porque los hilos de Generator sólo buscan Workers con true, y this es un worker con valor false que se actualiza a true él solo.
                // Además, esta operación de replace es atómica. Y además, como el Map es un ConcurrentHashMap, no hay posibles errores de ConcurrentModificationException porque varios
                // hilos lo recorran y modifiquen al mismo tiempo. Pero si dejo el bloque synchronized, tampoco bloquea mucho tiempo el Map workers porque es una operación atómica muy rápida,
                // y dejo el código aún más blindado a posibles errores derivados de concurrencia
                synchronized (workers) {
                    workers.replace(this, true);
                }
                semaforo.release();
            }
        }

    }
}
