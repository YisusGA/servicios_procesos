package ejercicio11_2026_11_16;

import java.util.*;
import java.util.concurrent.Semaphore;

public class Generator extends Thread {
    private final int numeroGenerado;
    private final Semaphore semaforo;
    private final Map<Worker, Boolean> workers;

    public Generator(Semaphore semaforo, Map<Worker, Boolean> workers) {
        this.numeroGenerado = generarNumeroAleatorio();
        this.semaforo = semaforo;
        this.workers = workers;
    }

    private int generarNumeroAleatorio() {
        Random random = new Random();
        return random.nextInt(1,11);
    }

    @Override
    public void run() {
        try {
            semaforo.acquire();
            List<Map.Entry<Worker, Boolean>> workerTrueList;
            // Todo esto debe ir en un bloque synchronized para evitar concurrencias de acceso al mismo dato
            synchronized (workers) {
                // Sacamos una List<Map.Entry<Worker, Boolean>> en la que todos los worker tengan un valor Boolean igual a true
                workerTrueList = workers.entrySet().stream().filter(x -> x.getValue() == true).toList();

                Random random = new Random();
                Optional<Map.Entry<Worker, Boolean>> randomTrueWorker = workerTrueList.isEmpty() ? Optional.empty() : Optional.of(workerTrueList.get(random.nextInt(workerTrueList.size())));
                // Si el Optional contiene un elemento, sacamos el elemento, que es un Map.Entry<Worker, Boolean>. Y le pasamos el número generado por Generator, reemplazamos ese worker en
                // el Map con su valor actualizado a false y lo interrumpimos
                randomTrueWorker.ifPresent(x -> {
                    x.getKey().setNumero(numeroGenerado);
                    workers.replace(x.getKey(), false);
                    x.getKey().interrupt();
                });
            }
        } catch (InterruptedException e) {
            System.out.printf("Soy hilo Generator con ID %s. No hay semáforo libre, me espero\n", threadId());
        }
    }
}
