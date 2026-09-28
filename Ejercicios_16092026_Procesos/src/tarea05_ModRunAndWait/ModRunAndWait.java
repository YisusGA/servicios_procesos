package tarea05_ModRunAndWait;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ModRunAndWait {
    static void main() {
        ProcessBuilder[] pbs = new ProcessBuilder[] {
                new ProcessBuilder("java", "-cp", ".\\out\\production\\Ejercicios_16092026_Procesos", "tarea04_Sumador.Sumador", "3", "6", "salidaSumador"),
                new ProcessBuilder("java", "-cp", ".\\out\\production\\Ejercicios_16092026_Procesos", "tarea04_Sumador.Sumador", "4", "7", "salidaSumador"),
                new ProcessBuilder("java", "-cp", ".\\out\\production\\Ejercicios_16092026_Procesos", "tarea04_Sumador.Sumador", "5", "8", "salidaSumador")
        };

        List<List<String>> argsList = new ArrayList<>();
        for (ProcessBuilder pb : pbs) {
            argsList.add(pb.command());
        }

        List<Process> procesos = new ArrayList<>();

        try {
            for (int i = 0; i < 3; i++) {
                List<String> currentArgs = argsList.get(i);
                ProcessBuilder currentPb = pbs[i];
                System.out.println("Comando lanzado para el proceso " + (i + 1) + ": ");
                currentArgs.forEach(x -> System.out.print(x + " "));
                System.out.println();
                File f = new File(currentArgs.getLast() + i + ".txt");
                currentPb.redirectOutput(f);
                procesos.add(currentPb.start());
            }
            // Esperar a que TODOS los procesos terminen antes de leer. Esto es indispensable, porque si no se hace,
            // surge una condición de carrera (race condition). Esto quiere decir que los procesos tardan un poco
            // en arrancarse y en volcar su salida a los txt. Pero el programa va a saltar muy rápidamente a la parte del
            // código que tiene que leer de los ficheros. Como esos ficheros aún no tienen los valores volcados, no se
            // va a sumar nada a la variable sumaTotal y valdrá 0. Esto no ocurre si se corre el programa en modo debug,
            // pues al correr el código con pausas, se da tiempo a que los procesos hayan escrito sus datos en los
            // ficheros antes de leer de esos ficheros y sumar a sumaTotal
            for (Process p : procesos) {
                p.waitFor(); // Frena la ejecución del programa principal hasta que el proceso p termine
            }

            int sumaTotal = 0;
            for (int i = 0; i < 3; i++) {
                List<String> currentArgs = argsList.get(i);
                File f = new File(currentArgs.getLast() + i + ".txt");
                BufferedReader br = new BufferedReader(new FileReader(f));
                List<String> lineas = br.readAllLines();
                for (String s : lineas) {
                    sumaTotal += Integer.parseInt(s);
                }
            }
            System.out.println("La suma total de todo es: " + sumaTotal);
        } catch (IOException e) {
            System.err.println("Error IO");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.println("Error de parseo de número");
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.err.println("Error de parseo de número");
            System.err.println("Error en el waitFor()");
            e.printStackTrace();
        }
    }
}
