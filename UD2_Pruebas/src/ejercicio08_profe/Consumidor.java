package ejercicio08_profe;

public class Consumidor extends Thread {
    private final Producto producto;

    Consumidor(Producto p) {
        this.producto = p;
    }

    @Override
    public void run() {
        int cantidadVista;
        try {
            while (true) {
                synchronized (producto) {
                    // Espera mientras no haya stock
                    while (producto.getCantidad() == 0) {
                        producto.wait();
                    }
                    cantidadVista = producto.getCantidad();
                }

                Thread.sleep(1000);

                synchronized (producto) {
                    if (cantidadVista == producto.getCantidad()) {
                        // Consumir una unidad
                        producto.setCantidad(cantidadVista - 1);
                        System.out.println("tareas.ejercicio8.Consumidor: " + this.getName() + " " + producto.getNombre() + " (quedan " + producto.getCantidad() + ")");
                    }

                    // Si se ha agotado, despertamos al productor
                    if (producto.getCantidad() == 0) {
                        producto.notifyAll();
                    }
                }

                // Pausa entre consumos (fuera del synchronized para no retener el cerrojo)
                Thread.sleep(800);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}