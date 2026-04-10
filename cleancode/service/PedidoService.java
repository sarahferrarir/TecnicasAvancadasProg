package cleancode.service;

import java.util.List;

import cleancode.model.Cliente;
import cleancode.model.Item;
import cleancode.model.Pedido;
import cleancode.repository.PedidoRepository;

public class PedidoService {

    private PedidoRepository repository;
    private DescontoService descontoService;
    private FreteService freteService;

    public PedidoService() {

        repository = new PedidoRepository();
        descontoService = new DescontoService();
        freteService = new FreteService();

    }

    public Pedido criarPedido(Cliente cliente, List<Item> itens) {

        int id = repository.findAll().size() + 1;

        Pedido pedido = new Pedido(id, cliente, itens);

        double subtotal = pedido.calcularSubtotal();

        double comDesconto =
                descontoService.aplicarDesconto(cliente.getTipo(), subtotal);

        double frete =
                freteService.calcularFrete(comDesconto);

        pedido.setTotal(comDesconto + frete);

        repository.save(pedido);

        return pedido;

    }

    public List<Pedido> listar() {
        return repository.findAll();
    }

    public Pedido buscar(int id) {
        return repository.findById(id);
    }

}