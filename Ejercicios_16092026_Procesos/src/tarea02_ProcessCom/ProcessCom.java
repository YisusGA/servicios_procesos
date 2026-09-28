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
                        System.out.println("Cosas que ha hecho el hijo");
                        // Leemos todas las líneas con el método readAllLines() de BufferedReader. Esto genera una
                        //  List<String>, que podemos recorrer con un for-each
                        for (String s : br.readAllLines()) {
                            System.out.println(s);
                        }
                        is.close();
                        br.close();
                        // Hacemos lo mismo con las salidas de errores que haya podido generar el hijo
                        // Hay que tener en cuenta que, cada vez que se corre un proceso, el proceso tiene 2 salidas de
                        // datos: la salida de datos estándar, y la salida de datos de errores. Y por eso, ambas se deben
                        // sacar mediante 2 streams diferentes
                        InputStream ise = p.getErrorStream();
                        BufferedReader bre = new BufferedReader(new InputStreamReader(ise));
                        System.out.println("Errores que ha generado el hijo");
                        for (String s : bre.readAllLines()) {
                            System.out.println(s);
                        }
                        ise.close();
                        bre.close();
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
