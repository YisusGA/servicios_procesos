package ejercicio01_2026_10_07;

import teclado.Teclado;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ejercicio01 {
    static void main() {
        // List donde vamos a ir almacenando los estados del hilo1
        List<Thread.State> estadosHilo1 = new ArrayList<>();
        System.out.println("¿Cuántos corredores(hilos) quieres?");
        int numHilos = Teclado.leerEntero();


        Corredor[] corredores = new Corredor[numHilos];

        for (int i = 0; i < corredores.length; i++) {
            corredores[i] = new Corredor("Corredor " + (i+1));
        }

        Thread[] hilos = new Thread[numHilos];
        for (int i = 0; i < corredores.length; i++) {
            hilos[i] = new Thread(corredores[i]);
        }
        // Almacenamos el estado del hilo1 antes de lanzarlo
        estadosHilo1.add(hilos[0].getState());

        for (Thread t : hilos) {
            t.start();
        }

        // Variable para ir almacenando temporalmente el estado del hilo1
        Thread.State estado;
        // Mientras el estado del hilo1 no sea TERMINATED, vamos almacenando sus estados en la List de estados,
        // comprobando que no se añadan duplicados
        while ((estado = hilos[0].getState()) != Thread.State.TERMINATED) {
            if (!estadosHilo1.contains(estado)) {
                estadosHilo1.add(estado);
            }
        }
        // Cuando salga del bucle anterior, es porque el estado de hilo1 ya es TERMINATED. Así que añadimos este estado
        // de TERMINATED, pero comprobando que no se haya añadido ya por lo que sea, para no añadirlo 2 veces
        if (!estadosHilo1.contains(hilos[0].getState())) {
            estadosHilo1.add(estado);
        }
        // Con esto de debajo, esperamos a que terminen todos y cada uno de los hilos antes de continuar ejecutando el
        // código del hilo principal (main). Por lo tanto, cuando terminen todos, se imprime en consola el mensaje.
        // Si se quita este bucle for, el mensaje se imprime al poco de arrancar el hilo principal, pues no se espera
        // a que terminen de ejecutarse los hilos antes de imprimirlo, y estos demoran un cierto tiempo en terminar
        for (Thread t : hilos) {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Todos los corredores han llegado a la meta");

        // Mostramos los estados por los que ha pasado el hilo1
        System.out.println("El hilo1 ha pasado por los siguientes estados:");
        for (Thread.State st : estadosHilo1) {
            System.out.println(st);
        }

    }
}

class Corredor implements Runnable {
    String name;
    Random random = new Random();

    public Corredor(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            System.out.printf("El %s avanza al paso %d\n", name, i);
            try {
                Thread.sleep(random.nextInt(100, 400));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
