package solid.strategy.frete;

import solid.interfaces.FreteStrategy;

public class FretePadrao implements FreteStrategy {

    @Override
    public double calcular(double valor) {
        return valor + 20;
    }
}