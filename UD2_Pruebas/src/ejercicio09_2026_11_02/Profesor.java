package ejercicio09_2026_11_02;

public class Profesor extends Thread {
    private String nombre;
    private final Aula aula;

    public Profesor(String nombre, Aula aula) {
        this.nombre = nombre;
        this.aula = aula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Aula getAula() {
        return aula;
    }

    @Override
    public void run() {
        synchronized (aula) {
            if (!aula.isHayProfesor()) {
                aula.setHayProfesor(!aula.isHayProfesor());
                System.out.printf("Soy el profesor %s y he llegado al aula %s. Comienza la clase\n", this.getNombre(), aula.getNombre());
                aula.notifyAll();
            }
        }
    }
}
