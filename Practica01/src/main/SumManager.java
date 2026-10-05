package main;

import java.io.*;
import java.util.Scanner;

public class SumManager {
    static void main() {
        ProcessBuilder pb = new ProcessBuilder("java", "-cp", ".\\out\\production\\Practica01", "sum.SumWorker");
        try {
            Process p = pb.start();
            // Creamos un buffer de escritura, que va a mandar los datos de salida estándar del proceso padre al proceso
            // hijo
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
            // Creamos un buffer de lectura, que va a leer los datos que mande el proceso hijo mediante su salida
            // estándar
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String num;
            Scanner scan = new Scanner(System.in);
            do {
                // Se pide por consola que se introduzca un número en formato de cadena
                System.out.println("Introduce un número entero");
                num = scan.nextLine();
                // Con el BufferedWriter, mandamos esa cadena al proceso hiji
                bw.write(num);
                // Y mandamos un salto de línea
                bw.newLine();
                // Esto es necesario para que se fuerce el envío de lo que se haya almacenado en el buffer al proceso
                // hijo. Si no se pone, no funciona
                bw.flush();
                // Y con el BufferedReader, se lee la línea que el hijo manda mediante su salida estándar
                System.out.println("Salida del proceso hijo impresa desde el proceso padre: " + br.readLine());
                // Y se continúan mandando líneas al hijo y leyéndolas mientras el número introducido por consola no sea
                // negativo
            } while (Integer.parseInt(num) >= 0);
            // Cuando se introduce un número negativo, se sale del bucle y se destruye el proceso
            System.out.println("Has introducido un número negativo, se acabó lo que se daba. Destruyo el proceso y san se acabó");
            p.destroy();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
