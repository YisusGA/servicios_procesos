package ejercicio05_ContadorVocales_2026_10_26;

import teclado.Teclado;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private int numHilos = 5;
    private static String texto;
    private static char[] arrayVocales = {'a', 'e', 'i', 'o', 'u'};

    static void main() {
        System.out.println("¿De qué texto quieres contar las vocales ?");
        texto = Teclado.leerCadena();
        List<Thread> contadoresVocales = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            ContadorVocales contadorVocal = new ContadorVocales(texto, arrayVocales[i]);
            contadoresVocales.add(contadorVocal);
            contadorVocal.start();
        }

        for (Thread c :  contadoresVocales) {
            try {
                c.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("Se han contado estas vocales: " + ContadorVocales.cuentaVocales);
    }
}
