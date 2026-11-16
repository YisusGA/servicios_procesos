package ejercicio11_2026_11_16;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class Main {
    private static int numeroGenerators = 10;
    private static int numeroWorkers = 10;
    static void main() {
        Semaphore semaforo = new Semaphore(numeroWorkers);
        Map<Worker, Boolean> workers = new HashMap<>();

        for (int i = 0; i < numeroWorkers; i++) {
            Worker w = new Worker(semaforo, workers, i);
            workers.put(w, false);
            w.start();
        }

        for (int i = 0; i < numeroGenerators; i++) {
            Generator g = new Generator(semaforo, workers);
            g.start();
        }
    }
}
