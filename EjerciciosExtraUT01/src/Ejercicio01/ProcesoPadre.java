package Ejercicio01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProcesoPadre {
    static void main() {
        ProcessBuilder pb = new ProcessBuilder("java", "-cp", "./out/production/EjerciciosExtraUT01", "Ejercicio01.ProcesoHijo");
        try {
            Process p = pb.start();
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            for (int i = 0; i < 5; i++) {
                // Se duerme el hilo principal (el proceso padre) durante 1000 milisegundos y luego se lee la salida
                // estándar del hijo. Los procesos hijos se crean en hilos diferentes y no se duermen
                Thread.sleep(1000);
                System.out.println("El hijo ha escrito esto: " + br.readLine());
            }
            p.destroy();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
