package Ej01;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Ejercicio01_BufferedWriter {
    public static void main(String[] args) {
        File file = new File("saludo.txt");
        {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                bw.write("Hola");
                bw.newLine();
                bw.write("Esto es PSP");
                bw.newLine();
                bw.write("Fin del fichero");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
