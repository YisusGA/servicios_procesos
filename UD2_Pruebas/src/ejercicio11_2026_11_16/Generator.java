package ejercicio11_2026_11_16;

import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Semaphore;

public class Generator extends Thread {
    private int numeroGenerado;
    private Semaphore semaforo;
    private Map<Worker, Boolean> workers;

    public Generator(Semaphore semaforo, Map<Worker, Boolean> workers) {
        this.numeroGenerado = generarNumeroAleatorio();
        this.semaforo = semaforo;
        this.workers = workers;
    }

    private int generarNumeroAleatorio() {
        Random random = new Random();
        return random.nextInt(11);
    }

    @Override
    public void run() {
        super.run();
        try {
            semaforo.acquire();
            workers.entrySet().stream().filter(x -> x.getValue() == true).findFirst().get().getKey().setNumero(numeroGenerado);
        } catch (InterruptedException e) {
            System.out.printf("Soy %s. No hay semaforo libre, me espero", getName());
            throw new RuntimeException(e);
        }
    }
}
