package solid.repository;

import solid.interfaces.PedidoRepository;
import solid.model.Pedido;

import java.util.ArrayList;
import java.util.List;

public class PedidoRepositoryMemory implements PedidoRepository {

    private List<Pedido> pedidos = new ArrayList<>();

    @Override
    public void salvar(Pedido pedido) {
        pedidos.add(pedido);
    }

    @Override
    public Pedido buscarPorId(int id) {

        return pedidos.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Pedido> listarTodos() {
        return pedidos;
    }
}