package ejercicio05_ContadorVocales_2026_10_26;

public class ContadorVocales extends Thread {
    public static int cuentaVocales;
    private String texto;
    private char vocal;

    public ContadorVocales(String texto, char vocal) {
        this.texto = texto;
        this.vocal = vocal;
    }

    // Usando sincronización de método completo
    public synchronized void contarVocales (String texto, char vocal) {
        for (char c : texto.toCharArray()) {

            if (c == vocal) {
                cuentaVocales++;
            }
        }
    }

    @Override
    public void run() {
        contarVocales(texto, vocal);
    }
}
