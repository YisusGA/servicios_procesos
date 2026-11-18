package ejercicio08_2026_11_02;

public class Consumidor extends Thread {
    private final Producto producto;
    private final int tiempoEspera;

    public Consumidor(Producto producto, int tiempoEspera) {
        this.producto = producto;
        this.tiempoEspera = tiempoEspera;
    }


    @Override
    public void run() {
        int cantidadVista;
        while (true) {
            synchronized (producto) {
                while (producto.getNumeroProductos() == 0) {
                    try {
                        producto.wait();
                    } catch (InterruptedException e) {
                        // Es fundamental en programación concurrente poner este Thread.currentThread().interrupt()
                        // dentro del catch. Esto es así porque al entrar en el catch, Java limpia la etiqueta de
                        // Interrupted del hilo (pone Thread.isInterrupted() a false). Por lo tanto, cualquier nivel
                        // superior del código no sabría que este hilo fue interrumpido y no podría gestionarlo. Al
                        // restaurar la etiqueta de Interrupted a true, se puede gestionar a nivel de código
                        Thread.currentThread().interrupt();
                        throw new RuntimeException("InterruptedException en el wait del Consumidor" + this.getName() + e);
                    }
                }
                cantidadVista = producto.getNumeroProductos();
            }

            // El tiempo de espera ha de quedar fuera de los bloques sincronizados, para que la espera de un cliente no
            // bloquee a los demás de entrar a mirar a la Tienda
            try {
                Thread.sleep(tiempoEspera);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("InterruptedException en el sleep del Consumidor" + this.getName() + e);
            }

            synchronized (producto) {
                if (producto.getNumeroProductos() == cantidadVista) {
                    producto.setNumeroProductos(cantidadVista - 1);
                    System.out.printf("Soy %s. He comprado 1 producto %s, ahora hay %d productos\n", this.getName(), producto.getNombre(), producto.getNumeroProductos());
                    producto.notifyAll();
                }
            }
        }
    }
}
