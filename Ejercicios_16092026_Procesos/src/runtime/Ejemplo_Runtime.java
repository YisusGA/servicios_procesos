package runtime;

import java.io.IOException;

public class Ejemplo_Runtime {
    static void main(String[] args) {
        Runtime r = Runtime.getRuntime();
        try {
            Process p = r.exec("ping google.com");
            System.out.println("Memory: " + r.freeMemory());
            System.out.println("Procesadores: " + r.availableProcessors());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
