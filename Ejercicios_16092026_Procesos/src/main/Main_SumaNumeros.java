package main;

import java.io.IOException;

public class Main_SumaNumeros {
    static void main() {
        // Los argumentos que pasamos son: java, para que la JVM ejecute el fichero .class; -cp, o classpath, para indicarle dónde están las clases; la ruta de los ficheros .class; paquete.class; arg1; arg2...
        ProcessBuilder pb = new ProcessBuilder( "java", "-cp", "out/production/Ejercicios_16092026_Procesos", "procesos01.SumaNumeros", "5", "6");
        {
            try {
                // Sets the source and destination for subprocess standard I/O to be the same as those of the current Java process
                // En la práctica, con esto conseguimos que, en este caso concreto, se redirija la salida a la consola actual
                pb.inheritIO();
                // Al lanzar el proceso con el método start() de ProcessBuilder, devuelve el propio proceso que hemos lanzado
                Process process = pb.start();
                // El método waitFor() de la clase Process, si no se le dan parámetros, devuelve el código de salida
                int exitCode = process.waitFor();
                System.out.println("El proceso terminó con código: " + exitCode);
            } catch (IOException e) {
                System.err.println("Problemita problemoso");
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
