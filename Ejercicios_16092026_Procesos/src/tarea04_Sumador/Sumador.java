package tarea04_Sumador;

public class Sumador {
    static void main(String[] args) {
//        if (args.length == 3) {
            int resultado = 0;
            for (int i = Integer.parseInt(args[0]); i <= Integer.parseInt(args[1]); i++) {
                resultado += i;
            }
            System.out.println(resultado);
//        }
    }
}
