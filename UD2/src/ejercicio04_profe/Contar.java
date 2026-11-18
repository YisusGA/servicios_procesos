// EJERCICIO 4
package ejercicio04_profe;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Contar {

    public static void main (String[] args) {
        System.out.print("Introduce el numero de hilos: ");

        Scanner sc = new Scanner(System.in);

        Cuenta cr = new Cuenta();

        List<Thread> threads = new ArrayList<>();

        int nThreads = sc.nextInt();

        for (int i = 0; i < nThreads; i++) {
            Thread t = new Thread(cr);
            t.start();
            threads.add(t);
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("Contador 1: " + cr.getC1());
        System.out.println("Contador 2: " + cr.getC2());
    }
}
