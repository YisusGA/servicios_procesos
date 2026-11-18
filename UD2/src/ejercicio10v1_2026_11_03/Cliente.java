package ejercicio10v1_2026_11_03;

import java.util.Random;

public class Cliente extends Thread {
    private final String nombre;
    private final GestorLavadoras gestorLavadoras;

    public Cliente(GestorLavadoras gestorLavadoras) {
        this.nombre = this.getName();
        this.gestorLavadoras = gestorLavadoras;
    }

    public String getNombre() {
        return nombre;
    }

    public GestorLavadoras getGestorLavadoras() {
        return gestorLavadoras;
    }

    @Override
    public void run() {
        Random random = new Random();
        Lavadora lavadora;
            synchronized (gestorLavadoras) {
                while (gestorLavadoras.getNumeroLavadorasDisponibles() == 0) {
                    try {
                        System.out.printf("Soy %s. Todas las lavadoras están ocupadas, me espero\n", this.getNombre());
                        gestorLavadoras.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException("Error de wait de Cliente " + this.getNombre() + e);
                    }
                }
                lavadora = gestorLavadoras.getLavadoraDisponible();
                if (lavadora != null) {
                    System.out.printf("Soy %s. Ocupo la lavadora %s\n", this.getNombre(), lavadora.getNombre());
                    gestorLavadoras.ocuparLavadora(lavadora);
                }
                gestorLavadoras.notifyAll();
            }

            try {
                Thread.sleep(random.nextInt(2000, 6000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Error de sleep de Cliente " + this.getNombre() + e);
            }

            synchronized (gestorLavadoras) {
                if (lavadora != null) {
                    System.out.printf("Soy %s. Libero la lavadora %s\n", this.getNombre(), lavadora.getNombre());
                    gestorLavadoras.liberarLavadora(lavadora);
                    gestorLavadoras.notifyAll();
                }
            }
    }
}
