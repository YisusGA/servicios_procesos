package practica_ut02.practica_ut2_magos;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Mago extends Thread {
    private final int energiaConsumidaPorHechizo;
    private final DepositoMagia deposito;
    private final Semaphore zonaLanzamiento;
    private final Random random;
    private int numeroHechizos;
    private int puntuacion;

    public Mago(DepositoMagia deposito, Semaphore zonaLanzamiento) {
        this.deposito = deposito;
        this.numeroHechizos = 10;
        this.zonaLanzamiento = zonaLanzamiento;
        random = new Random();
        puntuacion = 0;
        energiaConsumidaPorHechizo = 1;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public int getNumeroHechizos() {
        return numeroHechizos;
    }

    @Override
    public void run() {
        while (numeroHechizos > 0) {
            try {
                // Se intenta pillar un permiso de semafoto
                zonaLanzamiento.acquire();
                synchronized (deposito) {
                    while (deposito.getEnergia() == 0) {
                        // Como la energia es 0, se pone a dormir. Y despertará cuando un profesor rellene la energía
                        // del depósito y lance un notifyAll(). Importante que se llame con deposito.wait(), y no wait(),
                        // pues este segundo lanzaría un this.wait() (sobre mago)
                        deposito.wait();
                    }
                    Thread.sleep(random.nextInt(250, 751));
                    deposito.drenarEnergia(energiaConsumidaPorHechizo);
                    numeroHechizos--;
                    puntuacion += random.nextInt(5, 11);
                    System.out.printf("Soy mago %s. Expecto Patronum: ", getName());
                    for (int i = 0; i < 5; i++) {
                        System.out.printf("*");
                        Thread.sleep(300);
                    }
                    System.out.println();
                    // Importante que este deposito.notifyAll() vaya dentro del bloque synchronized, justo al final
                    deposito.notifyAll();
                }
                // Tiene sentido poner el zonaLanzamiento.release() fuera del bloque synchronized, pues se adquiere el
                // permiso antes de dicho bloque, y mejora la legibilidad por simetría visual. Además, tiene más sentido
                // a nivel de funcionamiento de la app que vaya fuera, pues sólo cuando el Mago ha terminado de usar
                // deposito y ya ha liberado su cerrojo, avisa a otros magos de que la zona de lanzamiento (semaforo) tiene
                // un permiso libre. Si se liberara el permiso dentro del bloque synchronized, otro Mago podría acceder
                // a la zona de lanzamiento, pero al no haber liberado el anterior mago aún el deposito, no podría tomar
                // energía para lanzar un hechizo
                zonaLanzamiento.release();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
