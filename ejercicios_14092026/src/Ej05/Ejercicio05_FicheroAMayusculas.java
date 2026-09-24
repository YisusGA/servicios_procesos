package Ej05;

import java.io.*;

public class Ejercicio05_FicheroAMayusculas {
    static void main() {
        File fileIn = new File("saludo.txt");
        File fileOut = new File("salidaMayusculas.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(fileIn)); BufferedWriter bw = new BufferedWriter(new FileWriter(fileOut))) {
            String line = "";
            while ((line = br.readLine()) != null) {
//                bw.write(line.toUpperCase() + "\n");
                bw.write(line.toUpperCase());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
