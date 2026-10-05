package Ejercicio02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ProcesoPadre {
    static void main() {
        ProcessBuilder pb = new ProcessBuilder("java", "-cp", "./out/production/EjerciciosExtraUT01", "Ejercicio02.ProcesoHijo");
        List<Process> procesos = new ArrayList<>();
        try {
            for (int i = 0; i < 5; i++) {
                Process p = pb.start();
                procesos.add(p);
            }
            // Se duerme el hilo principal (el proceso padre) durante %000 milisegundos y luego se lee la salida
            // estándar del hijo. Los procesos hijos se crean en hilos diferentes y no se duermen
            Thread.sleep(5000);
            for (int i = 0; i < procesos.size(); i++) {
                Process p = procesos.get(i);
                BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                System.out.println("El hijo número " + (i + 1) + " ha escrito: " + br.readLine());
                p.destroy();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
