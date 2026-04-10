package solid.service;

import solid.factory.DescontoFactory;
import solid.interfaces.DescontoStrategy;
import solid.interfaces.FreteStrategy;
import solid.interfaces.PedidoRepository;
import solid.model.*;

import java.util.List;

public class PedidoService {

    private PedidoRepository repository;
    private FreteStrategy frete;
    private DescontoFactory descontoFactory;

    private int contador = 1;

    public PedidoService(PedidoRepository repository,
                         FreteStrategy frete,
                         DescontoFactory descontoFactory) {

        this.repository = repository;
        this.frete = frete;
        this.descontoFactory = descontoFactory;
    }

    public Pedido criarPedido(Cliente cliente) {

        Pedido pedido = new Pedido(contador++, cliente);

        repository.salvar(pedido);

        return pedido;
    }

    public void adicionarItem(int id, Item item) {

        Pedido pedido = repository.buscarPorId(id);

        if (pedido != null) {
            pedido.adicionarItem(item);
        }
    }

    public void finalizarPedido(int id) {

        Pedido pedido = repository.buscarPorId(id);

        if (pedido == null) return;

        double total = pedido.getTotal();

        DescontoStrategy desconto =
                descontoFactory.criar(pedido.getCliente().getTipo());

        total = desconto.aplicar(total);

        total = frete.calcular(total);

        pedido.setTotal(total);

        pedido.setStatus(StatusPedido.FINALIZADO);
    }

    public Pedido buscarPedido(int id) {
        return repository.buscarPorId(id);
    }

    public List<Pedido> listarPedidos() {
        return repository.listarTodos();
    }
}