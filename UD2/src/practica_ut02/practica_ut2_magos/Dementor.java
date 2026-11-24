package practica_ut02.practica_ut2_magos;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Dementor extends Thread {
    private final DepositoMagia deposito;
    private final Semaphore zonaDeLanzamiento;
    private final int energiaDrenadaPorAcceso;
    private final Random random;

    public Dementor(DepositoMagia deposito, Semaphore zonaDeLanzamiento) {
        this.deposito = deposito;
        this.zonaDeLanzamiento = zonaDeLanzamiento;
        this.energiaDrenadaPorAcceso = 1;
        random = new Random();
    }

    @Override
    public void run() {
        try {
            while (true) {
                int accion = random.nextInt(2);
                if (accion == 0) {
                    synchronized (deposito) {
                        while (deposito.getEnergia() == 0) {
                            deposito.wait();
                        }
                        deposito.drenarEnergia(energiaDrenadaPorAcceso);
                        System.out.printf("Soy dementor %s, dreno energía\n", getName());
                        deposito.notifyAll();
                    }
                } else {
                    zonaDeLanzamiento.acquire();
                    System.out.printf("Soy dementor %s, ocupo la zona de lanzamiento\n", getName());
                    Thread.sleep(random.nextInt(700, 1500));
                    zonaDeLanzamiento.release();
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
