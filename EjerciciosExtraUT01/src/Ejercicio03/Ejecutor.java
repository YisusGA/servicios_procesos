package Ejercicio03;

import java.io.*;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Ejecutor {
    static void main() {
        Scanner scan = new Scanner(System.in);;
        String[] commands = scan.nextLine().split(" ");
        ProcessBuilder pb = new ProcessBuilder(commands);
        // Redirige errores a la salida estándar, para que el padre pueda recibir de la salida estándar del hijo si
        // surge un error
        pb.redirectErrorStream(true);
        try {
            Process p = pb.start();
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            // Mejor hacer esto que un br.readAllLines(), porque si el comando lanzado no termina nunca,
            // br.readAllLines() se queda esperando indefinidamente y nunca avanza el código hasta el p.waitFor().
            // Además, si el comando escribe muchas líneas, se puede desbordar el buffer. Y además, no se ve la salida
            // en tiempo real, pues hay que esperar a que termine de mandar líneas
            String result;
            while ((result = br.readLine()) != null) {
                System.out.println(result);
                System.out.flush();
            }
            p.waitFor();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
