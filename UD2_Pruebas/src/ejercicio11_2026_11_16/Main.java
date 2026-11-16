package ejercicio11_2026_11_16;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

public class Main {
    private static int numeroGenerators = 10;
    private static int numeroWorkers = 5;

    static void main() {
        Semaphore semaforo = new Semaphore(numeroWorkers);
        // Muy importante que sea ConcurrentHashMap, para que no
        // haya problemas de concurrencia de que más de 1 hilo Generator
        // acceda al mismo worker. El profe usa una List y elimina el Worker de la List
        // antes de operar con él, que es una solución más sencilla. Pero aún así, varios Generator pueden ejecutar
        // workers.removeFirst() exactamente al mismo tiempo, mientras otros Worker hacen workers.add(this). Como
        // ArrayList no está sincronizado, esto puede provocar: IndexOutOfBoundsException o NoSuchElementException
        // (si dos hilos intentan borrar a la vez); ConcurrentModificationException y que se pierda un Worker de la
        // lista o se duplique al redimensionar el array interno
        Map<Worker, Boolean> workers = new ConcurrentHashMap<>();

        for (int i = 0; i < numeroWorkers; i++) {
            Worker w = new Worker(semaforo, workers, i);
            workers.put(w, true);
            w.start();
        }

        for (int i = 0; i < numeroGenerators; i++) {
           new Generator(semaforo, workers).start();
        }
    }
}
