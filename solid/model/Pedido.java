package solid.model;

import java.util.ArrayList;
import java.util.List;

public class Pedido {

    private int id;
    private Cliente cliente;
    private List<Item> itens = new ArrayList<>();
    private StatusPedido status;
    private double total;

    public Pedido(int id, Cliente cliente) {
        this.id = id;
        this.cliente = cliente;
        this.status = StatusPedido.ABERTO;
    }

    public void adicionarItem(Item item) {
        itens.add(item);
        total += item.getPreco();
    }

    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<Item> getItens() {
        return itens;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Pedido " + id +
                " | Cliente: " + cliente.getNome() +
                " | Tipo: " + cliente.getTipo() +
                " | Total: " + total +
                " | Status: " + status;
    }
}