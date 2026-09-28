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
                // Esto parece que no hace nada porque se redirecciona la salida al fichero
//                InputStream is = p.getInputStream();
//                BufferedReader br = new BufferedReader(new InputStreamReader(is));
//                List<String> lineas = br.readAllLines();
                p.waitFor();

            System.out.println("Salida devuelta:");
//            for (String s : lineas) {
//                System.out.println(s);
//            }
//                br.close();
//                is.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
