package practica_ut02.practica_ut2_magosV2;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Mago extends Thread {

    // Variables susceptibles de ser tuneadas para ajustar la simulación
    private final int energiaConsumidaPorHechizo = 1;
    private final int numeroMaximoHechizos = 10;
    private final int tiempoDormirParaRenderer = 4000;
    // Variables que no se tocan por el usuario
    private final DepositoMagia deposito;
    private final Semaphore zonaLanzamiento;
    private final Random random;
    private final long id;
    private final Renderer renderer;
    private final String customName;
    private int puntuacion = 0;
    private int numeroHechizosDisponibles = numeroMaximoHechizos;

    public Mago(DepositoMagia deposito, Semaphore zonaLanzamiento, Renderer renderer, String customName) {
        this.deposito = deposito;
        this.zonaLanzamiento = zonaLanzamiento;
        random = new Random();
        id = threadId();
        this.renderer = renderer;
        this.customName = customName;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public int getNumeroHechizosDisponibles() {
        return numeroHechizosDisponibles;
    }

    public int getNumeroMaximoHechizos() {
        return numeroMaximoHechizos;
    }

    public String getCustomName() {
        return customName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Mago mago = (Mago) o;
        return id == mago.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public void run() {
        while (numeroHechizosDisponibles > 0) {
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
                    numeroHechizosDisponibles--;
                    puntuacion += random.nextInt(5, 11);
                    // Importante que este deposito.notifyAll() vaya dentro del bloque synchronized, justo al final
                    deposito.notifyAll();
                }
                zonaLanzamiento.release();
                // No hace falta meter un synchronized aquí, pues aunque varios magos accedan a la clase Renderer y a su método
                // lanzarHechizo, se trabaja sobre su recurso compartido, que es un ConcurrentHashMap, que hace un replace, que es
                // una operación atómica en un ConcurrentHashMap. Además, cada mago sólo puede modificarse a sí mismo, pues
                // las claves del ConcurrentHashMap son objetos Mago, cada mago llama al método que hace el replace usando su propia identidad
                // y el hashCode implementado en la clase Mago usa el threadId como condición de igualdad
//                String hechizo = "";
//                for (int i = 0; i < numeroHechizos; i++) {
//                    renderer.lanzarHechizo(hechizo, this);
//                    hechizo += "*";
//                    Thread.sleep(300);
//                }
//                renderer.lanzarHechizo("", this);
                renderer.accionMago(this, String.format("tiene este número de hechizos disponibles %s (%d/%d) y lanza expelliarmus\n", "*".repeat(numeroHechizosDisponibles), numeroHechizosDisponibles, numeroMaximoHechizos));
                // Tiene que dormir para que le dé tiempo al Renderer a imprimir que ha lanzado un hechizo
                Thread.sleep(tiempoDormirParaRenderer);
                renderer.accionMago(this, String.format("tiene este número de hechizos disponibles %s (%d/%d)\n", "*".repeat(numeroHechizosDisponibles), numeroHechizosDisponibles, numeroMaximoHechizos));
                // Tiene sentido poner el zonaLanzamiento.release() fuera del bloque synchronized, pues se adquiere el
                // permiso antes de dicho bloque, y mejora la legibilidad por simetría visual. Además, tiene más sentido
                // a nivel de funcionamiento de la app que vaya fuera, pues sólo cuando el Mago ha terminado de usar
                // deposito y ya ha liberado su cerrojo, avisa a otros magos de que la zona de lanzamiento (semaforo) tiene
                // un permiso libre. Si se liberara el permiso dentro del bloque synchronized, otro Mago podría acceder
                // a la zona de lanzamiento, pero al no haber liberado el anterior mago aún el deposito, no podría tomar
                // energía para lanzar un hechizo
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
