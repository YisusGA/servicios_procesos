package pruebas_2026_10_07;

public class Hilo {

    static void main(String[] args) {

        // Con esta forma, creo varias instancias de la clase MiHilo (que hereda de Thread). Y luego
        // lanzo un hilo de cada una. Los hilos no van a compartir atributos entre sí,
        // pues cada instancia tiene las suyas
//        MiHilo h1 = new MiHilo(1);
//        MiHilo h2 = new MiHilo(2);
//        MiHilo h3 = new MiHilo(3);
//        MiHilo h4 = new MiHilo(4);
//        h1.start();
//        h2.start();
//        h3.start();
//        h4.start();
        // No se usa h1.run() porque eso lanzaría el método run() de MiHilo, en lugar de lanzar el hilo en sí


        // Con esta forma, creo sólo una instancia de la clase MiHiloRunnable...
        MiHiloRunnable runnable = new MiHiloRunnable();
        //...y luego creo varios hilos que usan esa instancia.
        // Todos los atributos de la instancia de la clase MiHiloRunnable se compartirán entre todos los hilos
        Thread h1 = new Thread(runnable);
        Thread h2 = new Thread(runnable);
        Thread h3 = new Thread(runnable);
        h1.start();
        h2.start();
        h3.start();
    }

}

class MiHilo extends Thread {

    int num;
    MiHilo(int n){this.num = num;}
    @Override
    public void run() {
        System.out.println("Hola desde el hilo" + num);
    }
}

class MiHiloRunnable implements Runnable {

    @Override
    public void run() {
        System.out.println("Hola desde el hilo");
    }
}
