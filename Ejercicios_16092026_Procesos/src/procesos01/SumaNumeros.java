package procesos01;

public class SumaNumeros {
    static void main(String[] args) {
        if (args.length == 2) {
            int resultado = 0;
            for (int i = Integer.parseInt(args[0]); i <= Integer.parseInt(args[1]); i++) {
                resultado += i;
            }
            System.out.println("El resultado de la operacion es: " + resultado);
        }
    }
}
