package tarea05_ModRunAndWait;

import teclado.TecladoOK;

public class ExplicacionAlgoritmoIntervalos {
    static void main() {

        // TecladoOK es una clase escrita por mí para leer por teclado enteros, decimales y cadenas y validar mediante
        // bucles y try-catch que el valor introducido sea válido, para evitar Exceptions

        // Número de inicio
        System.out.println("Introduce número de inicio");
        int numInicio = TecladoOK.leerEntero();

        // Número de final. Se comprueba que sea mayor que el número de inicio
        int numFinal;
        do {
            System.out.println("Introduce número de final. Debe ser menor que el de inicio");
            numFinal = TecladoOK.leerEntero();
        } while (numFinal <= numInicio);

        // Número de divisiones. Se comprueba que no sea mayor que el número de números del intervalo
        int divisiones;
        do {
            System.out.println("Introduce número de divisiones");
            divisiones = TecladoOK.leerEntero();
        } while (divisiones > (numFinal - numInicio + 1));

        // Se calcula el paso de cada subintervalo, eliminando decimales
        int paso = (numFinal - numInicio) / divisiones;

        // En caso de que el módulo (resto) de la división anterior no fuera cero, se saca ese resto para sumárselo
        // al último subintervalo y que llegue hasta el número final
        int resto = (numFinal - numInicio) % divisiones;

        // El número final del primer subintervalo comienza siendo el número de inicio más el paso. De esta forma,
        // el primer subintervalo tendrá un número de números igual a paso + 1, los sucesivos subintervalos tendrán un
        // número de números igual al paso y el último subintervalo tendrá un número de números igual al paso (si el
        // resto fue 0) o igual al paso + resto (si el resto fue mayor que 0)
        numFinal = numInicio + paso;
        System.out.println("Paso: "+ paso);
        System.out.println("Resto: "+ resto);
        System.out.println();

        for (int i = 0; i < divisiones; i++) {
            System.out.println("Inicio: " + numInicio);
            System.out.println("Final: "+ numFinal);
            // Crear ProcessBuilder con parámetros numInicio y numFinal
            numInicio = numFinal + 1;
            if (resto != 0 && i == divisiones - 2) {
                numFinal += paso + resto;
            } else {
                numFinal += paso;
            }
        }
    }
}
