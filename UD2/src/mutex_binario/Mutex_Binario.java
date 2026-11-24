package mutex_binario;

import java.util.concurrent.Semaphore;

public class Mutex_Binario {
    // Solo 1 permiso → solo 1 persona puede entrar
    private static final Semaphore semaforito = new Semaphore(1);
    public static void main(String[] args) {
        Thread t = new Thread(new Persona("Ana", semaforito));
        t.start();
//        new Thread(new Persona("Ana", semaforito)).start();
        new Thread(new Persona("Luis", semaforito)).start();
        new Thread(new Persona("Marta", semaforito)).start();
        while (!t.getState().equals(Thread.State.TERMINATED)) {
            System.out.println(t.getState());
        }
        if (t.getState().equals(Thread.State.TERMINATED)) {
            System.out.println(t.getState());
        }
    }
}
