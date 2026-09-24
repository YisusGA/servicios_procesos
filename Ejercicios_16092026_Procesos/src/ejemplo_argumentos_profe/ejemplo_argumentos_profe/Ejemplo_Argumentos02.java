package ejemplo_argumentos_profe.ejemplo_argumentos_profe;

import java.io.IOException;

public class Ejemplo_Argumentos02 {
    static void main(String[] args) {
        ProcessBuilder pb = new ProcessBuilder("calc");
        try {
            pb.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
