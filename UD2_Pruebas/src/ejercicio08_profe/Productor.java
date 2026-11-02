package ejercicio08_profe;

public class Productor extends Thread {
    private final Producto producto;

    Productor(Producto p) {
        this.producto = p;
    }

    @Override
    public void run() {
        try {
            while (true) {
                synchronized (producto) {
                    // 1. El wait debe estar DENTRO del synchronized
                    // 2. Se invoca sobre el mismo objeto cerrojo: producto.wait()
                    while (producto.getCantidad() > 0) {
                        producto.wait();
                    }

                    // Reponer producto
                    producto.setCantidad(10);
                    System.out.println("tareas.ejercicio8.Productor: repuesto " + producto.getNombre());

                    // Avisar a los consumidores que esperan en este producto
                    producto.notifyAll();
                }

                // Pausa fuera de la sección crítica para dar margen al consumidor
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
