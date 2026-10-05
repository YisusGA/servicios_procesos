package sum;

import java.util.Scanner;

public class SumWorker {
    static void main() {
        // Esto no es necesariamente una entrada de teclado, lo que escanea es todo lo que entra por la entrada estándar
        // del programa. Y en este caso, sería el proceso padre que lo llame el que le pase datos mediante la entrada
        // estándar del proceso hijo
        Scanner scan = new Scanner(System.in);
        // Bucle infinito, pues queremos que este programa se ejecute sin parar hasta que el proceso padre que lo llamó
        // lo destruya
        while (true) {
            // Se parsean los datos que lee el Scanner de la entrada estándar
            int num = Integer.parseInt(scan.nextLine());
            // Se le suma 1, se convierte a String y se manda mediante salida estándar al proceso padre que lo llame.
            // Es super importante usar println y no print, pues en el proceso padre leemos una línea, y la forma que
            // tenemos de mandar una línea es println, que manda la línea con un carácter de nueva línea '\n' al final
            System.out.println((num + 1) + "");
        }
    }
}
