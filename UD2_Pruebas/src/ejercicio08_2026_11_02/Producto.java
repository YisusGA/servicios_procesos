package ejercicio08_2026_11_02;

public class Producto {
    private String nombre;
    private int numeroProductos;

    public Producto(String nombre, int numeroProductos) {
        this.nombre = nombre;
        this.numeroProductos = numeroProductos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumeroProductos() {
        return numeroProductos;
    }

    public void setNumeroProductos(int numeroProductos) {
        this.numeroProductos = numeroProductos;
    }
}
