package ejercicio08_profe;

public class Producto {
    private final String nombre;
    private int cantidad = 0;

    Producto(String n) {
        this.nombre = n;
    }

    public void setCantidad(int n) {
        this.cantidad = n;
    }

    public int getCantidad() {
        return cantidad;
    }

    public String getNombre() {
        return nombre;
    }
}

