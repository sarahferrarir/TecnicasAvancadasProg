package solid.strategy.desconto;

import solid.interfaces.DescontoStrategy;

public class DescontoComum implements DescontoStrategy {

    @Override
    public double aplicar(double valor) {
        return valor;
    }
}