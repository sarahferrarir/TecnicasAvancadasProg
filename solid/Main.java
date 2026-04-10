package solid;

import solid.factory.DescontoFactory;
import solid.interfaces.FreteStrategy;
import solid.interfaces.PedidoRepository;
import solid.repository.PedidoRepositoryMemory;
import solid.service.PedidoService;
import solid.service.RelatorioServiceImpl;
import solid.strategy.frete.FretePadrao;
import solid.system.Sistema;

public class Main {

    public static void main(String[] args) {

        PedidoRepository repository = new PedidoRepositoryMemory();

        FreteStrategy frete = new FretePadrao();

        DescontoFactory descontoFactory = new DescontoFactory();

        PedidoService pedidoService =
                new PedidoService(repository, frete, descontoFactory);

        RelatorioServiceImpl relatorioService =
                new RelatorioServiceImpl();

        Sistema sistema =
                new Sistema(pedidoService, relatorioService);

        sistema.run();
    }
}