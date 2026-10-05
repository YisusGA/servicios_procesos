package Ejercicio02;

public class ProcesoHijo {
    static void main() {
        while (true) {
            System.out.println("Hola soy uno de los hijos");
            // Fuerza el envío del texto al buffer, para que no se sature el buffer del hijo en los 5 segundos que
            // duerme el padre que lo llama. Porque Thread.sleep(x), lanzado desde el proceso padre, duerme al padre
            // pero no a sus hijos
            System.out.flush();
        }
    }
}
