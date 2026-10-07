package ejercicio01_2026_10_07;

import teclado.TecladoOK;

import java.util.Random;

public class Ejercicio01 {
    static void main() {
        System.out.println("¿Cuántos hilos quieres?");
        int numHilos = TecladoOK.leerEntero();

        Corredor corredor = new Corredor();
        Thread[] hilos = new Thread[numHilos];

        for (int i = 0; i < hilos.length; i++) {
            hilos[i] = new Thread(corredor);
        }
        for (Thread t : hilos) {
            t.start();
        }
        for (Thread t : hilos) {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Todos los corredores han llegado a la meta");

    }
}

class Corredor implements Runnable {

    int num;
//    Corredor() {this.num = num;}
    Random random = new Random();

    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            System.out.println("El corredor avanza al paso " + i);
            try {
                Thread.sleep(random.nextInt(100, 400));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
