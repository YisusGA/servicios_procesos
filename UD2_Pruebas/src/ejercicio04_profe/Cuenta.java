package ejercicio04_profe;

// Versión 1: Metodo syncronized
//public class Cuenta implements Runnable {
//    int c1;
//    int c2;
//
//    public synchronized void inc1() {c1++;}
//    public synchronized void inc2() {c2++;}
//
//    public int getC1() {return c1;}
//    public int getC2() {return c2;}
//
//    @Override
//    public void run() {
//        for (int i = 0; i < 100000; i++) {
//            inc1();
//            inc2();
//        }
//    }
//}

// Versión 2: Bloque syncronized
public class Cuenta implements Runnable {
    int c1;
    int c2;

    public void inc1() {c1++;}
    public void inc2() {c2++;}

    public int getC1() {return c1;}
    public int getC2() {return c2;}

    @Override
    public void run() {
        for (int i = 0; i < 100000; i++) {
            synchronized (this) {
                inc1();
            }
            synchronized (this) {
                inc2();
            }
        }
    }
}
