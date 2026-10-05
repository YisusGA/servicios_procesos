package tarea02_ProcessCom;

import java.io.*;
import java.util.Scanner;

public class ProcessCom {
    static void main(String[] args) {
        // Aquí no se crea bucle for, porque todo el ping es un argumento
        Scanner scan = new Scanner(System.in);
        System.out.println("""
                Quieres la salida por consola o en un fichero
                Introduce 1 para consola
                Introduce 2 para fichero
                """);
        ProcessBuilder pb = new ProcessBuilder(args);
        // Redirigimos el stream de errores del hijo a la salida estándar, para que si el hijo genera
        // errores, el padre pueda leerlos desde la salida estándar del hijo
        pb.redirectErrorStream(true);
        int choice;
        do {
            choice = scan.nextInt();
            switch (choice) {
                case 1 -> {
                    try {
                        Process p = pb.start();
                        // Conseguimos el InputStream, que es la semilla. Este InputStream es un flujo de bits que le
                        // pasa al proceso padre las salidas que ha generado el hijo (el padre recibe como Input las
                        // salidas del hijo)
                        InputStream is = p.getInputStream();
                        // Codificamos a caracteres ese streams de bits mediante un BufferedReader
                        BufferedReader br = new BufferedReader(new InputStreamReader(is));
                        System.out.println("Cosas o errores que ha generado el hijo");
                        // Mejor hacer esto que un br.readAllLines(), porque si el comando lanzado no termina nunca,
                        // br.readAllLines() se queda esperando indefinidamente y nunca avanza el código hasta el p.waitFor().
                        // Además, si el comando escribe muchas líneas, se puede desbordar el buffer. Y además, no se ve la salida
                        // en tiempo real, pues hay que esperar a que termine de mandar líneas
                        String s;
                        while ((s = br.readLine()) != null) {
                            System.out.println(s);
                        }
                        is.close();
                        br.close();
                        p.waitFor();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                case 2 -> {
                    File f = new File("salida.txt");
                    pb.redirectOutput(f);
                    try {
                        pb.start();
                        System.out.println("Fichero escrito: " + f.getAbsolutePath());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                default -> {
                    System.err.println("Valor no válido");
                }
            }
        } while (choice != 1 && choice != 2);
    }
}
