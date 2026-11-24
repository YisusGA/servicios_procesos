package practica_ut02.practica_ut2_magosV2;

import java.util.Map;

public class Renderer extends Thread {

    private final DepositoMagia deposito;
    private final Map<Mago, String> magos;
    private final Map<Dementor, String> dementores;
    private final Map<Profesor, Boolean> profesores;

    public Renderer(DepositoMagia deposito, Map<Mago, String> magos, Map<Dementor, String> dementores, Map<Profesor, Boolean> profesores) {
        this.deposito = deposito;
        this.magos = magos;
        this.dementores = dementores;
        this.profesores = profesores;
    }

    public void accionMago(Mago mago, String input) {
        magos.replace(mago, input);
    }
    public void accionDementor(Dementor dementor, String input) {
        dementores.replace(dementor, input);
    }
    public void accionProfesor(Profesor profesor, Boolean input) {
        profesores.replace(profesor, input);
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Esto hace que si se lanza el programa desde la terminal CMD, se limpie la terminal antes de imprimir la siguiente tanda
                System.out.print("\033[H\033[2J");
                System.out.flush();

                if (magos != null && !magos.isEmpty() && dementores != null && !dementores.isEmpty() && profesores != null && !profesores.isEmpty()) {

                    for (Map.Entry<Profesor, Boolean> entryProfesor : profesores.entrySet()) {
                        System.out.printf(entryProfesor.getValue() == true ? "Profesor %s repone energía del depósito\n" : "", entryProfesor.getKey().getCustomName());
                    }

                    System.out.println();
                    for (Map.Entry<Mago, String> entryMago : magos.entrySet()) {
                        System.out.printf("%s %s", entryMago.getKey().getCustomName(), entryMago.getValue());
                    }

                    System.out.println();
                    for (Map.Entry<Dementor, String> entryDementor : dementores.entrySet()) {
                        System.out.printf(entryDementor.getValue());
                    }

                    int energiaDeposito = 0;
                    synchronized (deposito) {
                        energiaDeposito = deposito.getEnergia();
                    }
                    System.out.println("\nEnergía del depósito\n--------------------");
                    int energiaRestanteAImprimir = energiaDeposito;
                    while (energiaRestanteAImprimir > 0) {
                        int imprimirEnEstaLinea = Math.min(5, energiaRestanteAImprimir);
                        for (int j= 0; j < imprimirEnEstaLinea; j++) {
                            System.out.print("*");
                        }
                        energiaRestanteAImprimir -= imprimirEnEstaLinea;
                        System.out.println();
                    }
                    System.out.println("----------------------------------------------");
                    // Esto da tiempo para leer la salida, antes de que se limpie la CMD si se lanza desde ahí, o antes de
                    // que se desplace el texto de la terminal del IDE si se lanza desde el IDE
                    Thread.sleep(2000);
                }
//                Thread.sleep(150);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
