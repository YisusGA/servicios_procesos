package practica_ut02.practica_ut2_magosV2;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Dementor extends Thread {

    // Variables susceptibles de ser tuneadas para ajustar la simulación
    private final int energiaDrenadaPorAcceso = 1;
    private final int tiempoDormirParaRenderer = 4000;

    // Variables que no se tocan por el usuario
    private final DepositoMagia deposito;
    private final Renderer renderer;
    private final Semaphore zonaDeLanzamiento;
    private final long id;
    private final String customName;
    private final Random random;

    public Dementor(DepositoMagia deposito, Renderer renderer, Semaphore zonaDeLanzamiento, String customName) {
        this.deposito = deposito;
        this.renderer = renderer;
        this.zonaDeLanzamiento = zonaDeLanzamiento;
        this.id = threadId();
        this.random = new Random();
        this.customName = customName;
    }

    public String getCustomName() { return customName; }

    @Override
    public void run() {
        try {
            while (true) {
                int accion = random.nextInt(2);
                String textoAccion = "";
                if (accion == 0) {
                    synchronized (deposito) {
                        while (deposito.getEnergia() == 0) {
                            deposito.wait();
                        }
                        deposito.drenarEnergia(energiaDrenadaPorAcceso);
                        deposito.notifyAll();
                        textoAccion = String.format("Dementor %s en acción. Dreno energía del depósito\n", getCustomName());
                    }
                } else {
                    zonaDeLanzamiento.acquire();
                    int tiempoOcupacion = random.nextInt(700, 1500);
                    Thread.sleep(tiempoOcupacion);
                    zonaDeLanzamiento.release();
                    textoAccion = String.format("Dementor %s en acción. Ocupo la zona de lanzamiento durante %d ms\n", getCustomName(), tiempoOcupacion);
                }
                renderer.accionDementor(this, textoAccion);
                Thread.sleep(tiempoDormirParaRenderer);
                renderer.accionDementor(this, "");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
