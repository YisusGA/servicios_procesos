package Ej04;

import java.io.*;

public class Ejercicio04_CopiaFichero {
    static void main() {
        File fileIn = new File("saludo.txt");
        File fileOut = new File("salida.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(fileIn)); BufferedWriter bw = new BufferedWriter(new FileWriter(fileOut))) {
            String line;
            while ((line = br.readLine()) != null) {
//                bw.write(line + "\n");
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
