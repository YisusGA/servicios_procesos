package ejercicio04_2026_10_25;

import java.util.ArrayList;
import java.util.List;


public class Contar {
    static void main() {
        int numeroHilos = 2;
        Cuenta cuenta = new Cuenta();
        List<Thread> hilos = new ArrayList<>();

       long tiempoInicial = System.nanoTime();
        for (int i = 0; i < numeroHilos; i++) {
            hilos.add(new Thread(cuenta));
            hilos.get(i).start();
        }

        for (Thread t :hilos) {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.printf("Tiempo transcurrido: %d ms\n", (System.nanoTime() - tiempoInicial) / 1000000);

        System.out.println("Valor final c1: " + cuenta.getCuenta1());
        System.out.println("Valor final c2: " + cuenta.getCuenta2());
    }
}

class Cuenta implements Runnable {
    int cuenta1 = 0;
    int cuenta2 = 0;

    public int getCuenta1() {
        return cuenta1;
    }

    public int getCuenta2() {
        return cuenta2;
    }

    public void incrementa1() {cuenta1++;}
    public void incrementa2(){cuenta2++;}

    @Override
    public void run() {
        for (int i = 0; i < 100000; i++) {
            synchronized (this) {
                incrementa1();
            }
            synchronized (this) {
                incrementa2();
            }
        }
    }
}

