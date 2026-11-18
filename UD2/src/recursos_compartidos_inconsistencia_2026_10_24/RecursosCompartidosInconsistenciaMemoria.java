package recursos_compartidos_inconsistencia_2026_10_24;

public class RecursosCompartidosInconsistenciaMemoria extends Thread {
    private static int contador;

    public static void main(String[] args) {
        for(int i=0;i<1000;i++) {
            new RecursosCompartidosInconsistenciaMemoria().start();
        }

        try {
            Thread.sleep(1000);
        }catch(Exception e) {
            e.printStackTrace();
        }

        System.out.println("Valor de contador: "+contador);
    }

    public void run() {
        for(int i=0;i<1000;i++) {
            contador++;
        }
    }

}
