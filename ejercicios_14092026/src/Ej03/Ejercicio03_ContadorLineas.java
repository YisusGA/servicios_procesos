package Ej03;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Ejercicio03_ContadorLineas {
    public static void main(String[] args) {
        File file = new File("saludo.txt");
        {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                int contadorLineas = 0;
                ;
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println("Linea " + ++contadorLineas + " " + line);
                }
                System.out.println("Total de líneas: " + contadorLineas);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
