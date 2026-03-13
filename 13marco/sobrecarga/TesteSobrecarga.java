package sobrecarga;
import java.util.Scanner;

public final class TesteSobrecarga {
    private TesteSobrecarga() {
    }
    /**
     * Testar a calculadora.
     * @param args args
     */
    public static void main(final String[] args) {
        Scanner teclado = new Scanner(System.in);

        Calculadora calc = new Calculadora();
        System.out.println(calc.somar(teclado.nextInt(), teclado.nextInt()));
        System.out.println(calc.somar(
                teclado.nextInt(), teclado.nextInt(), teclado.nextInt()));
        System.out.println(calc.somar(
                teclado.nextDouble(), teclado.nextDouble()));
    }
}
