package tarea05_ModRunAndWait;

import teclado.TecladoOK;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ModRunAndWait {

    // Creamos una lista donde vamos a almacenar toods los ProcessBuilder que creemos
    private static final List<ProcessBuilder> pbs = new ArrayList<>();
    // Creamos una lista de los Process que vamos a lanzar, para luego poder recorrerlos con un for-each que haga
    // que el programa espere a que terminen todos antes de ejecutar el código que viene a continuación
    private static final List<Process> procesos = new ArrayList<>();
    // Creamos una lista que contenga las listas de argumentos de cada ProcessBuilder
    private static final List<List<String>> argsList = new ArrayList<>();

    static void main() {

        // TecladoOK es una clase escrita por mí para leer por teclado enteros, decimales y cadenas y validar mediante
        // bucles y try-catch que el valor introducido sea válido, para evitar Exceptions
        System.out.println("Introduce número de inicio");
        int numInicio = TecladoOK.leerEntero();

        int numFinal;
        do {
            System.out.println("Introduce número de final. Debe ser menor que el de inicio");
            numFinal = TecladoOK.leerEntero();
        } while (numFinal <= numInicio);

        int divisiones;
        do {
            System.out.println("Introduce número de divisiones");
            divisiones = TecladoOK.leerEntero();
        } while (divisiones > (numFinal - numInicio + 1));

        int paso = (numFinal - numInicio) / divisiones;

        int resto = (numFinal - numInicio) % divisiones;

        numFinal = numInicio + paso;

        try {
            // Lanzamos los métodos que están a continuación del método main
            sumarIntervalos(numInicio, numFinal, divisiones, paso, resto);
            sumarTotalIntervalos(divisiones);
        } catch (IOException e) {
            System.err.println("Error IO o de archivo no encontrado");
            e.printStackTrace();
        }  catch (InterruptedException e) {
            System.err.println("Error en el waitFor() para esperar a que finalicen los procesos");
            e.printStackTrace();
        }
    }

    static void sumarIntervalos(int numInicio, int numFinal, int divisiones, int paso, int resto) throws IOException, InterruptedException {

        for (int i = 0; i < divisiones; i++) {
            // Creamos el ProcessBuilder con los valores del subintervalo pasados como parámetros
            pbs.add(new ProcessBuilder("java", "-cp", ".\\out\\production\\Ejercicios_16092026_Procesos", "tarea04_Sumador.Sumador", "" + numInicio, "" + numFinal, "salidaSumador"));
            // Actualizamos los valores de inicio y final del intervalo
            numInicio = numFinal + 1;
            if (resto != 0 && i == divisiones - 2) {
                numFinal += paso + resto;
            } else {
                numFinal += paso;
            }
        }

        // Sacamos los args de todos los ProcessBuilder y los almacenamos en una lista
        for (ProcessBuilder pb : pbs) {
            argsList.add(pb.command());
        }

        // Sacamos y mostramos los args de cada proceso, redirigimos la salida estándar de cada proceso a un fichero
        // diferente e iniciamos cada proceso
        for (int i = 0; i < divisiones; i++) {
            List<String> currentArgs = argsList.get(i);
            ProcessBuilder currentPb = pbs.get(i);
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
    }

    static void sumarTotalIntervalos(int divisiones) throws IOException {
        // Accedemos a cada fichero, pillamos el número que tiene almacenado y lo vamos sumando en una variable
        int sumaTotal = 0;
        for (int i = 0; i < divisiones; i++) {
            List<String> currentArgs = argsList.get(i);
            File f = new File(currentArgs.getLast() + i + ".txt");
            BufferedReader br = new BufferedReader(new FileReader(f));
            List<String> lineas = br.readAllLines();
            for (String s : lineas) {
                sumaTotal += Integer.parseInt(s);
            }
        }
        System.out.println("La suma total de todo es: " + sumaTotal);
    }
}