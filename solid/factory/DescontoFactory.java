package solid.factory;

import solid.interfaces.DescontoStrategy;
import solid.model.TipoCliente;
import solid.strategy.desconto.DescontoComum;
import solid.strategy.desconto.DescontoPremium;
import solid.strategy.desconto.DescontoVip;

public class DescontoFactory {

    public DescontoStrategy criar(TipoCliente tipo) {

        return switch (tipo) {

            case PREMIUM -> new DescontoPremium();

            case VIP -> new DescontoVip();

            default -> new DescontoComum();
        };
    }
}