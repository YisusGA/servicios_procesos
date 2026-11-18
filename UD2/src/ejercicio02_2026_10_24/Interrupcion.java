package ejercicio02_2026_10_24;

public class Interrupcion {
    static void main() {
        Hilo hiloHijo = new Hilo();
        hiloHijo.start();
        System.out.println("Soy el hilo padre, me espero 1 segundo");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Soy el hilo padre, interrumpo el hilo hijo para ver cómo lo gestiona");
        // Esto genera una InterruptedException en el hiloHijo, que la captura mediante su catch
        hiloHijo.interrupt();
        try {
            hiloHijo.join();
            System.out.println("Soy el hilo padre. Confirmo que el hilo hijo ha finalizado. Su estado es: ");
            System.out.println(hiloHijo.getName() + " " + hiloHijo.getState());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class Hilo extends Thread {
    @Override
    public void run() {
        try {
            System.out.println("Soy el hilo hijo y espero 5 segundos");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            try {
                System.out.println("Soy el hilo hijo. Mi padre me ha interrumpido, me enfado y espero 2 segundos más");
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
