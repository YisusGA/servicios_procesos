package ejercicio08_2026_11_02;

import java.util.ArrayList;
import java.util.List;

public class Tienda {
    private static int numeroConsumidores = 10;
    private static int tiempoDeEspera = 1000;
    private static int numeroDeProductosAReponer = 10;

    static void main() {
        List<Thread> hilos = new ArrayList<>();

        Producto RAM = new Producto("RAM",10);
        Producto CPU = new Producto("CPU",10);
        hilos.add(new Productor(RAM, numeroDeProductosAReponer));
        hilos.add(new Productor(CPU, numeroDeProductosAReponer));
        for (int i = 0; i < numeroConsumidores / 2; i++) {
            hilos.add(new Consumidor(RAM, tiempoDeEspera));
            hilos.add(new Consumidor(CPU, tiempoDeEspera));
        }
        for (Thread t : hilos) {
            t.start();
        }
    }
}
