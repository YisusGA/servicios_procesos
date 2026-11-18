package ejercicio10v1_2026_11_03;

import java.util.Objects;

public class Lavadora {
    private String nombre;

    public Lavadora(String nombre) {
        this.nombre = nombre;
    }

    public Lavadora() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Lavadora lavadora = (Lavadora) o;
        return Objects.equals(nombre, lavadora.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nombre);
    }
}
