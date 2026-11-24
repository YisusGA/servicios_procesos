package practica_ut02.practica_ut2_magos;

public class DepositoMagia {
    private int energia;

    public DepositoMagia(int energia) {
        this.energia = energia;
    }

    public int getEnergia() {
        return energia;
    }

    public void rellenarEnergia(int cantidad) {
        this.energia += cantidad;
    }

    public void drenarEnergia(int cantidad) {
        energia -= cantidad;
        if (energia < 0) {
            energia = 0;
        }
    }
}
