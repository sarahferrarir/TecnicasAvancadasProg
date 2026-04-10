package solid.strategy.desconto;

import solid.interfaces.DescontoStrategy;

public class DescontoPremium implements DescontoStrategy {

    @Override
    public double aplicar(double valor) {
        return valor * 0.9;
    }
}