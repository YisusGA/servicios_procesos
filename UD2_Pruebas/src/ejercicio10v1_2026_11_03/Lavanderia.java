package ejercicio10v1_2026_11_03;

import java.util.ArrayList;
import java.util.List;

public class Lavanderia {
    private static final int numeroLavadoras = 3;
    private static final int numeroTecnicos = 1;
    private static final int numeroClientes = 10;
    private static final int frecuenciaRevision = 2000;
    private static final int tiempoEsperaRevision = 4000;

    static void main() {

        List<Thread> clientes = new ArrayList<>();
        List<Thread> tecnicos = new ArrayList<>();

        GestorLavadoras gestorLavadoras = new GestorLavadoras(numeroLavadoras);

        for (int i = 0; i < numeroTecnicos; i++) {
            tecnicos.add(new Tecnico(gestorLavadoras, frecuenciaRevision, tiempoEsperaRevision));
            // Al poner los hilos de Tecnico como daemon (vigilantes), el hilo principal no se queda esperando a que los
            // hilos de Tecnico finalicen, pues nunca lo harán, dando que en su run tienen un bucle infinito while(true).
            // Es importante que se declaren como Daemon antes de arrancarlos
            tecnicos.get(i).setDaemon(true);
        }

        for (int i = 0; i < numeroClientes; i++) {
            clientes.add(new Cliente(gestorLavadoras));
        }

        for (Thread t : clientes) {
            t.start();
        }

        // Esperamos a que todos los hilos de Cliente finalicen, y el hilo principal finalizará cuando terminen todos
        // los Cliente, pues los Tecnico, aunque se ejecuten de forma infinita, son daemon
        for (Thread t : tecnicos) {
            t.start();
        }

        for (Thread t : clientes) {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}