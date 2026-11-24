package practica_ut02.practica_ut2_magosV2;

import java.util.Random;

public class Profesor extends Thread {

    // Variables susceptibles de ser tuneadas para ajustar la simulación
    private final int cantidadEnergiaQueRepone = 10;
    private final int tiempoDormirParaRenderer = 4000;

    // Variables que no se tocan por el usuario
    private final DepositoMagia deposito;
    private final Renderer renderer;
    private final long id;
    private final String customName;
    private final Random random;

    public Profesor(DepositoMagia deposito, Renderer renderer, String customName) {
        this.deposito = deposito;
        this.renderer = renderer;
        this.id = threadId();
        this.random = new Random();
        this.customName = customName;
    }

    public String getCustomName() { return customName; }

    @Override
    public void run() {
        try {
            while (true) {
                synchronized (deposito) {
                    while (deposito.getEnergia() != 0) {
                        deposito.wait();
                    }
                    deposito.rellenarEnergia(cantidadEnergiaQueRepone);
                    Thread.sleep(random.nextInt(400, 801));
                    deposito.notifyAll();
                }
                renderer.accionProfesor(this, true);
                Thread.sleep(tiempoDormirParaRenderer);
                renderer.accionProfesor(this, false);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
