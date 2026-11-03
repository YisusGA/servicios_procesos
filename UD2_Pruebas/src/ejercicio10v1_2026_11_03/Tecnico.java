package ejercicio10v1_2026_11_03;

public class Tecnico extends Thread {
    private final String nombre;
    private final GestorLavadoras gestorLavadoras;
    private int frecuenciaRevision;
    private int tiempoEsperaRevision;

    public Tecnico(GestorLavadoras gestorLavadoras, int frecuenciaRevision, int tiempoEsperaRevision) {
        this.nombre = this.getName();
        this.gestorLavadoras = gestorLavadoras;
        this.frecuenciaRevision = frecuenciaRevision;
        this.tiempoEsperaRevision = tiempoEsperaRevision;
    }

    public String getNombre() {
        return nombre;
    }

    public GestorLavadoras getGestorLavadoras() {
        return gestorLavadoras;
    }

    public int getFrecuenciaRevision() {
        return frecuenciaRevision;
    }

    public void setFrecuenciaRevision(int frecuenciaRevision) {
        this.frecuenciaRevision = frecuenciaRevision;
    }

    public int getTiempoEsperaRevision() {
        return tiempoEsperaRevision;
    }

    public void setTiempoEsperaRevision(int tiempoEsperaRevision) {
        this.tiempoEsperaRevision = tiempoEsperaRevision;
    }

    @Override
    public void run() {
        Lavadora lavadora;
        long tiempoInicioColapso = 0;
        boolean estabaColapsado = false;
        while (true) {
            try {
                Thread.sleep(frecuenciaRevision);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Error de sleep de Técnico " + this.getNombre() + e);
            }

            // El profe trabaja haciendo un gestorLavadoras.wait(4000) dentro de este bloque synchronized. Y eso está mal,
            // porque si cualquiera de los Clientes libera una lavadora y lanza un gestorLavadoras.notifyAll(), despertará
            // al Técnico antes de que hayan pasado los 4 segundos. Además, en lo que el Técnico hace un wait, los Clientes
            // siguen haciendo cosas, por lo podría ocurrir, por ejemplo, que una Lavadora se libere durante 3 segundos mientras el
            // Técnico duerme, y que se ocupe de nuevo antes de que él despierte, por lo que el Técnico la vio ocupada
            // antes y después de haberse dormido, pensando que ha estado ocupada de forma continua durante 4 segundos,
            // cuando realmente no ha sido así
            synchronized (gestorLavadoras) {
                if (gestorLavadoras.getNumeroLavadorasDisponibles() == 0) {
                    if (!estabaColapsado) {
                        // Es el primer momento en que detectamos el colapso, y pillamos el tiempo de inicio del colapso.
                        estabaColapsado = true;
                        tiempoInicioColapso = System.currentTimeMillis();
                    } else {
                        // Ya estaba colapsado antes, comprobamos cuánto tiempo lleva de forma continua
                        long tiempoTranscurrido = System.currentTimeMillis() - tiempoInicioColapso;

                        if (tiempoTranscurrido > tiempoEsperaRevision) { // Más 4 segundos reales seguidos
                            lavadora = gestorLavadoras.getLavadoraAlAzar();
                            if (lavadora != null) {
                                gestorLavadoras.liberarLavadora(lavadora);
                                System.out.printf("[TECNICO] Soy %s, ha habido un colapso continuo que ha durado %d ms. Libero la lavadora %s\n", this.getNombre(), tiempoTranscurrido, lavadora.getNombre());
                                gestorLavadoras.notifyAll();
                            }
                            // Reiniciamos el estado del colapso tras actuar
                            estabaColapsado = false;
                        }
                    }
                } else {
                    // Si en alguna revisión encontramos al menos una lavadora libre,
                    // se rompe la continuidad y reseteamos el cronómetro de inmediato.
                    estabaColapsado = false;
                }
            }
        }
    }
}
