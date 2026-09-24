package tarea02_ProcessCom;


import teclado.TecladoOK;
import java.io.*;

public class ProcessComExternalJARs {
    static void main(String[] args) {
        // Aquí no se crea bucle for, porque todo el ping es un argumento
        System.out.println("""
                Quieres la salida por consola o en un fichero
                Introduce 1 para consola
                Introduce 2 para fichero
                """);
        ProcessBuilder pb = new ProcessBuilder(args);
        int choice;
        do {
            choice = TecladoOK.leerEntero();
            switch (choice) {
                case 1 -> {
                    try {
                        Process p = pb.start();
                        // Conseguimos el InputStream, que es la semilla. Este InputStream
                        InputStream is = p.getInputStream();
                        BufferedReader br = new BufferedReader(new InputStreamReader(is));
                        System.out.println("Cosas que ha hecho el hijo");
                        for (String s : br.readAllLines()) {
                            System.out.println(s);
                        }

                        InputStream ise = p.getErrorStream();
                        BufferedReader bre = new BufferedReader(new InputStreamReader(ise));
                        System.out.println("Errores que ha generado el hijo");
                        for (String s : bre.readAllLines()) {
                            System.out.println(s);
                        }
                    } catch (IOException e) {
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
