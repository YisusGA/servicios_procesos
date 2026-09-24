package tarea01_execprograms;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ExecPrograms {
    static void main(String[] args) {
        for (String arg : args) {

            // Con esto de debajo, se abre la CMD y se fuerza a que cada proceso que lancemos desde ahí 4 segundos,
            // para que no se cierre rápido cuando termine. Esta parte de tiempo sólo funciona como argumento si se le
            // pasa a la cmd, pues es un comando de CMD de Windows

//            ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", arg + " & timeout /t 4 /nobreak > nul");

            // Con esta forma que he hecho yo, no es necesario usar la cmd ni hacer un timeout, pues lanzo procesos
            // directamente asociándole los programas
            ProcessBuilder pb = new ProcessBuilder(arg);
            try {
                Process p = pb.start();
                // Imprimir la información del proceso
                p.info().command().ifPresent(comando -> {
                    File file = new File(comando);
                    System.out.println("Nombre proceso: " + file.getName());
                    System.out.println("Ruta proceso: " + comando);
                });
                System.out.println("PID: " + p.pid());
                p.info().startInstant().ifPresent(start -> System.out.println("Momento de inicio: " + start));

//                System.out.println("¿El proceso está vivo antes de la espera? " + (p.isAlive() ? "Sí" : "No"));

                // Mantiene el proceso padre esperando abierto durante 3 segundos
                p.waitFor(3, TimeUnit.SECONDS);

                // 2. Destruir el proceso DESPUÉS del tiempo transcurrido
//                p.destroy(); // o p.destroyForcibly() si fuera necesario
                // Como Windows crea un proceso hijo a partir del principal para abrir la app, tenemos que obtener sus descendientes y luego para cada uno, matarlos.
                // Aquí estamos usando la expresión lambda abreviada que vimos con Raquel en Acceso a datos
//                p.descendants().forEach(ProcessHandle::destroy);
//                p.destroy();
                // Por algún motivo, no funciona el cierre de proceso para la calculadora. Parece ser que Windows abre otro proceso desligado tras lanzar el calc.exe

                System.out.println("¿El proceso está vivo después del destroy? " + (p.isAlive() ? "Sí" : "No"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
