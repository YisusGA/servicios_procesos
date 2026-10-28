package ejercicio07_2026_10_28;

import java.util.ArrayList;
import java.util.List;

public class ContarV2 {
    private static final int numHilos = 5;

    static void main() {
        ContadorV2 contadorV2 = new ContadorV2();
        List<Thread> hilos = new ArrayList<>();

        long tiempoInicial = System.nanoTime();
        for (int i = 0; i < numHilos; i++) {
            Thread t = new Thread(contadorV2);
            hilos.add(t);
            t.start();
        }

        for (Thread t : hilos) {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.printf("Tiempo transcurrido: %d ms\n", (System.nanoTime() - tiempoInicial) / 1000000);
    }
}

class ContadorV2 implements Runnable {
    private final int tiempoEspera = 500;
    private final int iteraciones = 10;
    private int cuenta = 0;

    public void incrementar() {
        try {
            Thread.sleep(tiempoEspera);
            synchronized (this) {
                cuenta++;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void run() {
        for (int i = 0; i < iteraciones; i++) {
            incrementar();
        }
    }
}