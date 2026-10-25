package prioridad_hilos_2026_10_24;

public class PrioridadHilos {
    public static void main(String[] args) {
        ContadorHilos hilo1 = new ContadorHilos("Hilo 1");
        hilo1.setPriority(10);
        ContadorHilos hilo2 = new ContadorHilos("Hilo 2");
        hilo2.setPriority(1);
        hilo1.start();
        hilo2.start();
    }
}

class ContadorHilos extends Thread{
    String nombre;
    public ContadorHilos(String nombre){
        this.nombre=nombre;
    }
    public void run() {
        int count=0;
        while(count <= 1000) {
            System.out.println(nombre + ":" + count++);
        }
    }
}
