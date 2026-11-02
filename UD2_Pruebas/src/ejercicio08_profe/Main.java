package ejercicio08_profe;

public class Main {

    public static void main(String[] args) {

        Producto manzana = new Producto("Manzana");

        Productor p1 = new Productor(manzana);

        Consumidor c1 = new Consumidor(manzana);
        Consumidor c3 = new Consumidor(manzana);
        Consumidor c4 = new Consumidor(manzana);

        // Si quisiereamos podemos añadir nuevos productos, nuevos productores de ese producto y consumidores
        Producto pera = new Producto("Pera");
        Productor p2 = new Productor(pera);
        Consumidor c2 = new Consumidor(pera);

        p1.start();
        p2.start();
        c1.start();
        c2.start();
        c3.start();
        c4.start();
    }
}



