package tarea04_Sumador;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class RunAndWait {
    static void main() {
        ProcessBuilder pb = new ProcessBuilder("java", "-cp", ".\\out\\production\\Ejercicios_16092026_Procesos", "tarea04_Sumador.Sumador", "3", "6", "salidaSumador");
        try {
            // ProcessBuilder.inheritIO() configura los flujos estándar de entrada, salida y error del subproceso para que se hereden directamente de los del proceso Java actual (normalmente la consola o terminal desde la que ejecutas tu programa),
            // en lugar de crear pipes separados para leerlos o escribirlos manualmente
            pb.inheritIO();
            List<String> args = pb.command();
            System.out.println("Comando lanzado:");
            for (String s : args) {
                System.out.print(s + " ");
            }
            System.out.println();
            File f = new File(args.getLast() + ".txt");
            // Si no hiciéramos este pb.redirectOutput(f);, el System.out.println() del proceso Sumador saldría por la consola, pues es la salida del proceso padre RunAndWait, y lanzamos antes el pb.inheritIO();
            // EN cualquier caso, la salida de errores del proceso hijo seguirá saliendo a la consola del padre, pues ProcessBuilder.RedirectOutput(File f) sólo redirecciona la salida estándar del hijo
            pb.redirectOutput(f);
            Process p = pb.start();
            System.out.println("Proceso terminado con código: " + p.waitFor());
            System.out.println("Resultado escrito en fichero: " + f.getName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
