package cleancode.repository;

import java.util.ArrayList;
import java.util.List;

import cleancode.model.Pedido;

public class PedidoRepository {

    private List<Pedido> pedidos = new ArrayList<>();

    public void save(Pedido pedido) {

        pedidos.add(pedido);

    }

    public List<Pedido> findAll() {

        return pedidos;

    }

    public Pedido findById(int id) {

        return pedidos.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);

    }

}
