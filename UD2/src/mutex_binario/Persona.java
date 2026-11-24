package mutex_binario;

import java.util.concurrent.Semaphore;

public class Persona implements Runnable {
    private final String nombre;
    private final Semaphore semaforito;

    public Persona(String nombre, Semaphore semaforito) {
        this.nombre = nombre;
        this.semaforito = semaforito;
    }

    public String getNombre() {
        return nombre;
    }

    public void run() {
        System.out.println(nombre + " quiere entrar al baño...");
        try {
            semaforito.acquire(); // espera si ya hay alguien dentro

            System.out.println(nombre + " ha entrado al baño.");
            Thread.sleep(2000); // usando el baño…

            System.out.println(nombre + " ha salido del baño.");
        } catch (InterruptedException e) {
            // Aquí sólo se entra si otro hilo lo interrumpe, no entra si intenta pillar un permiso del semáforo y no hay
            // permisos disponibles. Si no hay permisos disponibles, permanece dormido hasta que otro hilo lance un release()
            // sobre el semáforo
            Thread.currentThread().interrupt();

        } finally {
            semaforito.release(); // deja entrar al siguiente
        }
    }

}
