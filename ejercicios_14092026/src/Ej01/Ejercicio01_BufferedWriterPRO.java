package Ej01;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class Ejercicio01_BufferedWriterPRO {
    static void main() {
        List<String> lineas = List.of("Hola", "Esto es PSP", "Fin del fichero");
        File outputFile = new File("userOutputData/saludo.txt");
        if (!outputFile.getParentFile().exists()) {
            try {
                Files.createDirectory(outputFile.getParentFile().toPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        {
            try(BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile, false))) {
                for (String linea : lineas) {
                    bw.write(linea);
                    bw.newLine();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
