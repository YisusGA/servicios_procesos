package ejercicio08_2026_11_02;

public class Productor extends Thread {
    private final Producto producto;
    private final int numeroDeProductosAReponer;

    public Productor(Producto producto, int numeroDeProductosAReponer) {
        this.producto = producto;
        this.numeroDeProductosAReponer = numeroDeProductosAReponer;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (producto) {
                while (producto.getNumeroProductos() > 0) {
                    try {
                        producto.wait();
                    } catch (InterruptedException e) {
                        // Es fundamental en programación concurrente poner este Thread.currentThread().interrupt()
                        // dentro del catch. Esto es así porque al entrar en el catch, Java limpia la etiqueta de
                        // Interrupted del hilo (pone Thread.isInterrupted() a false). Por lo tanto, cualquier nivel
                        // superior del código no sabría que este hilo fue interrumpido y no podría gestionarlo. Al
                        // restaurar la etiqueta de Interrupted a true, se puede gestionar a nivel de código
                        Thread.currentThread().interrupt();
                        throw new RuntimeException("InterruptedException en el wait del Productor" + this.getName() + e);
                    }
                }
                producto.setNumeroProductos(numeroDeProductosAReponer);
                System.out.printf("Soy %s. He repuesto los productos %s, ahora hay %d productos\n", this.getName(), producto.getNombre(), producto.getNumeroProductos());
                producto.notifyAll();
            }
        }
    }
}
