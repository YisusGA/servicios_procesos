package practica_ut02.practica_ut2_magos;

import java.util.Random;

public class Profesor extends Thread {
    private final DepositoMagia deposito;
    private final int cantidadEnergiaQueRepone;
    private final Random random;

    public Profesor(DepositoMagia deposito) {
        this.deposito = deposito;
        this.cantidadEnergiaQueRepone = 10;
        random = new Random();
    }

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
                    System.out.printf("Soy profesor %s, repongo energía\n", getName());
                    deposito.notifyAll();
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
