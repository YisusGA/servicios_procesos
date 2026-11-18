package ejercicio10v1_2026_11_03;

import java.util.*;

public class GestorLavadoras {
    private int numeroLavadorasTotales;
    // El value es true si está disponible, y false si está ocupada
    private Map<Lavadora, Boolean> lavadoras;

    public GestorLavadoras(int numeroLavadoras) {
        lavadoras = new HashMap<>();
        this.numeroLavadorasTotales = numeroLavadoras;
        for (int i = 0; i < numeroLavadoras; i++) {
            lavadoras.put(new Lavadora("Lavadora " + (i + 1)), true);
        }
    }

    public int getNumeroLavadorasTotales() {
        return numeroLavadorasTotales;
    }

    public void setNumeroLavadorasTotales(int numeroLavadorasTotales) {
        this.numeroLavadorasTotales = numeroLavadorasTotales;
    }

    public int getNumeroLavadorasDisponibles() {
        int numeroLavadorasDisponibles = 0;
        for (Map.Entry<Lavadora,Boolean> entry : lavadoras.entrySet()) {
            if (entry.getValue() == true) {
                numeroLavadorasDisponibles++;
            }
        }
        return numeroLavadorasDisponibles;
    }

    /**
     * Método que busca y ocupa la lavadora que se pasa como parámetro
     * @param lavadora la lavadora que se intenta ocupar
     * @return true si se ocupó la lavadora (pasando su value en el Map a false), false si no
     */
    public boolean ocuparLavadora(Lavadora lavadora) {
        boolean ocupada = false;
       Map.Entry<Lavadora, Boolean> entryLavadora = lavadoras.entrySet().stream().filter(x -> x.getKey().equals(lavadora)).findFirst().orElseGet(() -> null);
       if(entryLavadora != null) {
           lavadoras.replace(entryLavadora.getKey(), false);
           ocupada = true;
       }
       return ocupada;
   }

    /**
     * Método que busca y libera la lavadora que se pasa como parámetro
     * @param lavadora la lavadora que se intenta liberar
     * @return true si se liberó la lavadora (pasando su value en el Map a true), false si no
     */
    public boolean liberarLavadora(Lavadora lavadora) {
        boolean liberada = false;
        Map.Entry<Lavadora, Boolean> entryLavadora = lavadoras.entrySet().stream().filter(x -> x.getKey().equals(lavadora)).findFirst().orElseGet(() -> null);
        if(entryLavadora != null) {
            lavadoras.replace(entryLavadora.getKey(), true);
            liberada = true;
        }
        return liberada;
    }

    /**
     * Método que devuelve la primera lavadora disponible, o null si no la hay
     * @return la lavadora disponible, o null si no la hay
     */
    public Lavadora getLavadoraDisponible() {
        Lavadora lavadora = null;
        boolean encontrada = false;
        Map.Entry<Lavadora, Boolean> entryLavadora = lavadoras.entrySet().stream().filter(x -> x.getValue() == true).findFirst().orElseGet(() -> null);
        if (entryLavadora != null) {
            lavadora = entryLavadora.getKey();
        }
        return lavadora;
    }

    /**
     * Método que devuelve una lavadora al azar, o null si no encuentra ninguna
     * @return una lavadora al azar, o null si no encuentra ninguna
     */
    public Lavadora getLavadoraAlAzar() {
        Random random = new Random();
        Lavadora lavadora = null;
        List<Lavadora> listLavadoras = lavadoras.keySet().stream().toList();
        if (!listLavadoras.isEmpty()) {
            // El límite superior bound del random.nextInt(origin, bound) siempre es excluido. Se podría simplificar
            // poniendo lavadora = listLavadoras.get(random.nextInt(listLavadoras.size())); que ya comienza en 0
            lavadora = listLavadoras.get(random.nextInt(0, listLavadoras.size()));
        }
        return lavadora;
    }
}
