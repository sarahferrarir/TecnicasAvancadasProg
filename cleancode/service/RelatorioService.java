package cleancode.service;

import java.util.List;

import cleancode.model.Pedido;

public class RelatorioService {

    public void gerar(List<Pedido> pedidos) {

        double total = 0;

        for (Pedido p : pedidos) {

            total += p.getTotal();

            System.out.println(
                    p.getId() + " - " +
                    p.getCliente().getNome() + " - " +
                    p.getTotal()
            );
        }

        System.out.println("Total: " + total);

    }

}

