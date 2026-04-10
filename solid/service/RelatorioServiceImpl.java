package solid.service;

import solid.interfaces.RelatorioService;
import solid.model.Pedido;

import java.util.List;

public class RelatorioServiceImpl implements RelatorioService {

    @Override
    public void gerarRelatorio(List<Pedido> pedidos) {

        double total = 0;

        System.out.println("\n=== RELATORIO ===");

        for (Pedido p : pedidos) {

            System.out.println(p);

            total += p.getTotal();
        }

        System.out.println("Total geral: " + total);
        System.out.println("Quantidade: " + pedidos.size());
    }
}