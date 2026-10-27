package ejercicio04_2026_10_25;

import java.util.ArrayList;
import java.util.List;

public class Contar {
    static void main() {
        int numeroHilos = 2;
        Cuenta ct = new Cuenta();
        List<Thread> hilos = new ArrayList<>();

        for (int i = 0; i < numeroHilos; i++) {
            hilos.add(new Thread(new Contador(ct)));
            hilos.get(i).start();
        }

        for (Thread t : hilos) {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("Valor final c1: " + ct.getCuenta1());
        System.out.println("Valor final c2: " + ct.getCuenta2());
    }
}

class Cuenta {
    int cuenta1 = 0;
    int cuenta2 = 0;

    public int getCuenta1() {
        return cuenta1;
    }

    public int getCuenta2() {
        return cuenta2;
    }

    public void incrementa1() {
        cuenta1++;
    }

    public void incrementa2() {
        cuenta2++;
    }
}

class Contador implements Runnable {

    Cuenta cuenta;

    public Contador(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100000; i++) {
            synchronized (cuenta) {
                cuenta.incrementa1();
                cuenta.incrementa2();
            }
        }
    }
}
