package ejercicio11_2026_11_16;

import java.util.Map;
import java.util.concurrent.Semaphore;

public class Worker extends Thread {
    private int numero;
    private Semaphore semaforo;
    // El value del Boolean es false si no está disponible y true si lo está
    private Map<Worker, Boolean> workers;
    private int posicionEnMapa;

    public Worker(Semaphore semaforo, Map<Worker, Boolean> workers, int posicionEnMapa) {
        this.semaforo = semaforo;
        this.workers = workers;
        this.posicionEnMapa = posicionEnMapa;
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
                System.out.printf("Soy %s. Mi variable vale: %d\n", threadId(), numero * 1000);
                workers.replace(this, true);
                semaforo.release();
            }
        }

    }
}
