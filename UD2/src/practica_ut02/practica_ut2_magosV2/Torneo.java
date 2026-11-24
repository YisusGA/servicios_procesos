package practica_ut02.practica_ut2_magosV2;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

public class Torneo {
    // Variables tuneables para ajustar la simulación. La app es completamente escalable
    private final static int capacidadZonaDeLanzamiento = 2;
    private final static int energiaInicialDeposito = 10;
    private final static int numeroMagos = 4;
    private final static int numeroDementores = 2;
    private final static int numeroProfesores = 1;
    private final static List<String> nombresMagos = List.of("Harry Potter", "Cedric Diggory", "Hermione Granger", "Bellatrix Lestrange");
    private final static List<String> nombresDementores = List.of("Paco", "Eufrasio");
    private final static List<String> nombresProfesores = List.of("Alastor Moody");

    static void main(String[] args) {
        Semaphore zonaDeLanzamiento = new Semaphore(capacidadZonaDeLanzamiento);
        DepositoMagia deposito = new DepositoMagia(energiaInicialDeposito);

        Map<Mago, String> magos = new ConcurrentHashMap<>();
        Map<Dementor, String> dementores = new ConcurrentHashMap<>();
        Map<Profesor, Boolean> profesores = new ConcurrentHashMap<>();


        Renderer renderer = new Renderer(deposito, magos, dementores, profesores);
        renderer.setDaemon(true);

        for (int i = 0; i < numeroMagos; i++) {
            Mago mago = new Mago(deposito, zonaDeLanzamiento, renderer, nombresMagos.get(i));
            magos.put(mago, String.format("tiene este número de hechizos disponibles %s (%d/%d)\n", "*".repeat(mago.getNumeroHechizosDisponibles()), mago.getNumeroHechizosDisponibles(), mago.getNumeroMaximoHechizos()));
            mago.start();
        }

        renderer.start();

        // Al poner los hilos como daemon (vigilantes), el hilo principal no se queda esperando a que los
        // hilos que son daemon finalicen, pues nunca lo harán, dando que en su run tienen un bucle infinito while(true).
        // Es importante que se declaren como Daemon antes de arrancarlos
        for (int i = 0; i < numeroDementores; i++) {
            Dementor dementor = new Dementor(deposito, renderer, zonaDeLanzamiento, nombresDementores.get(i));
            dementor.setDaemon(true);
            dementores.put(dementor, "");
            dementor.start();
        }

        for (int i = 0; i < numeroProfesores; i++) {
            Profesor profesor = new Profesor(deposito, renderer, nombresProfesores.get(i));
            profesor.setDaemon(true);
            profesores.put(profesor, false);
            profesor.start();
        }

        // Esperamos a que todos los hilos de Mago finalicen, y el hilo principal finalizará cuando terminen todos
        // los Mago, pues los Dementor y Profesor, aunque se ejecuten de forma infinita, son daemon
        for (Map.Entry<Mago, String> entryMago : magos.entrySet()) {
            try {
                entryMago.getKey().join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            renderer.interrupt();
            renderer.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Torneo terminado");
        for (Map.Entry<Mago, String> entryMago : magos.entrySet()) {
            System.out.printf("%s con puntuación: %d y %s\n", entryMago.getKey().getName(), entryMago.getKey().getPuntuacion(), entryMago.getKey().getNumeroHechizosDisponibles() == 0 ? "no me quedaron hechizos por lanzar" : "me quedaron hechizos por lanzar");
        }

        System.out.println("Queda esta energía en el depósito: " + deposito.getEnergia());


    }
}
