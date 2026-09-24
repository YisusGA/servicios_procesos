package Ej02;

import java.io.*;

public class Ejercicio02_BufferedReader {
    public static void main(String[] args) {
        File file = new File("saludo.txt");
        {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
