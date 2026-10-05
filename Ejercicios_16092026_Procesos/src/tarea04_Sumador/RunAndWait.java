package tarea04_Sumador;

import java.io.*;
import java.util.List;

public class RunAndWait {
    static void main() {
        ProcessBuilder pb = new ProcessBuilder( "java", "-cp", ".\\out\\production\\Ejercicios_16092026_Procesos", "tarea04_Sumador.Sumador", "3", "6", "salidaSumador");
        try {
            pb.inheritIO();
            List<String> args = pb.command();
            System.out.println("Comando lanzado:");
            for (String s : args) {
                System.out.print(s + " ");
            }
            System.out.println();
                File f = new File(args.getLast() + ".txt");
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
