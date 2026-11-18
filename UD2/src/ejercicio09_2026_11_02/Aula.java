package ejercicio09_2026_11_02;

public class Aula {
    private String nombre;
    private boolean hayProfesor;

    public Aula(String nombre, boolean hayProfesor) {
        this.nombre = nombre;
        this.hayProfesor = hayProfesor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isHayProfesor() {
        return hayProfesor;
    }

    public void setHayProfesor(boolean hayProfesor) {
        this.hayProfesor = hayProfesor;
    }
}
