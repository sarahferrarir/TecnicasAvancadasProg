package cleancode.service;

import cleancode.model.TipoCliente;

public class DescontoService {

    public double aplicarDesconto(TipoCliente tipo, double subtotal) {

        switch (tipo) {

            case COMUM:
                return subtotal > 300 ? subtotal * 0.95 : subtotal;

            case PREMIUM:
                return subtotal > 200 ? subtotal * 0.90 : subtotal * 0.97;

            case VIP:
                return subtotal * 0.85;

            default:
                return subtotal;
        }
    }

}