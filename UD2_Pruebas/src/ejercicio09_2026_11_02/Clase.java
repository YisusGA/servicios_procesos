package ejercicio09_2026_11_02;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Clase {
    private static int puestoLlegadaProfe;
    private static final int numeroAlumnos = 10;

    static void main() {
        List<Thread> personas = new ArrayList<>();

        Aula DAM2 = new Aula("DAM2", false);

        Random random = new Random();
//        puestoLlegadaProfe = random.nextInt(0, numeroAlumnos - 1);
        puestoLlegadaProfe = 4;
        for (int i = 0; i < numeroAlumnos; i++) {
            if (i == puestoLlegadaProfe) {
                personas.add(new Profesor("Gabriel", DAM2));
            }
            personas.add(new Alumno(DAM2, "Alumno " + (i + 1)));
        }
        for (Thread t : personas) {
            t.start();
        }
    }
}
