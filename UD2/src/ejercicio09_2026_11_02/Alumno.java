package ejercicio09_2026_11_02;

public class Alumno extends Thread {
    private String nombre;
    private final Aula aula;

    public Alumno(Aula aula, String nombre) {
        this.aula = aula;
        this.nombre = nombre;
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
            while (!aula.isHayProfesor()) {
                try {
                    System.out.printf("Soy %s, alumno de %s. Como no hay profesor, me espero\n", this.getNombre(), aula.getNombre());
                    aula.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.printf("Soy %s, alumno de %s. Hola profe\n", this.getNombre(), aula.getNombre());
            aula.notifyAll();
        }
    }
}
