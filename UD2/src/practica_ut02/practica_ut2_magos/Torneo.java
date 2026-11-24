package practica_ut02.practica_ut2_magos;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Torneo {
    private final static int capacidadZonaDeLanzamiento = 2;
    private final static int energiaInicialDeposito = 10;
    private final static int numeroMagos = 4;
    private final static int numeroDementores = 2;
    private final static int numeroProfesores = 1;

    static void main() {
        Semaphore zonaDeLanzamiento = new Semaphore(capacidadZonaDeLanzamiento);
        DepositoMagia deposito = new DepositoMagia(energiaInicialDeposito);

        List<Mago> magos = new ArrayList<>();
        List<Dementor> dementores = new ArrayList<>();
        List<Profesor> profesores = new ArrayList<>();

        for (int i = 0; i < numeroMagos; i++) {
            Mago mago = new Mago(deposito, zonaDeLanzamiento);
            magos.add(mago);
            mago.start();
        }

        // Al poner los hilos como daemon (vigilantes), el hilo principal no se queda esperando a que los
        // hilos que son daemon finalicen, pues nunca lo harán, dando que en su run tienen un bucle infinito while(true).
        // Es importante que se declaren como Daemon antes de arrancarlos
        for (int i = 0; i < numeroDementores; i++) {
            Dementor dementor = new Dementor(deposito, zonaDeLanzamiento);
            dementor.setDaemon(true);
            dementores.add(dementor);
            dementor.start();
        }

        for (int i = 0; i < numeroProfesores; i++) {
            Profesor profesor = new Profesor(deposito);
            profesor.setDaemon(true);
            profesores.add(profesor);
            profesor.start();
        }



        // Esperamos a que todos los hilos de Mago finalicen, y el hilo principal finalizará cuando terminen todos
        // los Mago, pues los Dementor y Profesor, aunque se ejecuten de forma infinita, son daemon
        for (Mago m : magos) {
            try {
                m.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("Torneo terminado");
        for (Mago m : magos) {
            System.out.printf("Mago %s con puntuación: %d y %s\n", m.getName(), m.getPuntuacion(), m.getNumeroHechizos() == 0 ? "no me quedaron hechizos por lanzar" : "me quedaron hechizos por lanzar");
        }

        System.out.println("Queda esta energía en el depósito: " + deposito.getEnergia());


    }
}
