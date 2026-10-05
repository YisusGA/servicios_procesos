package Ejercicio03;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Principal {
    static void main(String[] args) {
        String arg = String.join(" ", args);
        if (arg.isBlank()) {
            arg = "ping 127.0.0.1"; // Valor por defecto si no se le pasan argumentos
        }
        ProcessBuilder pb = new ProcessBuilder("java", "-cp", "./out/production/EjerciciosExtraUT01", "Ejercicio03.Ejecutor");
        try {
            Process p = pb.start();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            bw.write(arg);
            bw.newLine();
            bw.flush();
            bw.close();

            // Mejor hacer esto que un br.readAllLines(), porque si el proceso hijo no termina nunca de mandar líneas,
            // br.readAllLines() se queda esperando indefinidamente y nunca avanza el código hasta el p.waitFor().
            // Además, si el proceso hijo manda muchas líneas, se puede desbordar el buffer. Y además, no se ve la salida
            // en tiempo real, pues hay que esperar a que termine de mandar líneas
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            p.waitFor(); // Esperar a la finalización del Ejecutor
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
