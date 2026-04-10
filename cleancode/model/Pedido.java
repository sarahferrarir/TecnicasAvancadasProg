package cleancode.model;

import java.util.List;

public class Pedido {

    private int id;
    private Cliente cliente;
    private List<Item> itens;
    private double total;
    private StatusPedido status;

    public Pedido(int id, Cliente cliente, List<Item> itens) {

        this.id = id;
        this.cliente = cliente;
        this.itens = itens;
        this.status = StatusPedido.NOVO;

    }

    public double calcularSubtotal() {

        return itens.stream()
                .mapToDouble(Item::getSubtotal)
                .sum();

    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void cancelar() {
        status = StatusPedido.CANCELADO;
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

    public double getTotal() {
        return total;
    }

    public StatusPedido getStatus() {
        return status;
    }
}