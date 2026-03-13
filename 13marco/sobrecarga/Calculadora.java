package sobrecarga;

public class Calculadora {
    /**
     * Metodo para calcular a soma entre 2 numeros inteiros.
     * @param a primeiro numero.
     * @param b segundo numero.
     * @return O resultado da soma.
     */
    public int somar(final int a, final int b) {
        return a + b;
    }

    /**
     * Metodo para calcular a soma entre 3 numeros inteiros.
     * @param a primeiro numero
     * @param b segundo numero
     * @param c terceiro numero
     * @return o resultado da soma.
     */
    public int somar(final int a, final int b, final int c) {
        return a + b + c;
    }
    /**
     * Metodo para calcular a soma entre 2 numeros nao inteiros.
     * @param a primeiro numero.
     * @param b segundo numero.
     * @return O resultado da soma.
     */
    public double somar(final double a, final double b) {
        return a + b;
    }
}
