package solid.system;

import solid.model.*;
import solid.service.PedidoService;
import solid.service.RelatorioServiceImpl;

import java.util.List;
import java.util.Scanner;

public class Sistema {

    private PedidoService pedidoService;
    private RelatorioServiceImpl relatorioService;
    private Scanner scanner = new Scanner(System.in);

    public Sistema(PedidoService pedidoService,
                   RelatorioServiceImpl relatorioService) {
        this.pedidoService = pedidoService;
        this.relatorioService = relatorioService;
    }

    public void run() {

        int opcao;

        do {
            menu();
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1 -> novoPedido();
                case 2 -> listarPedidos();
                case 3 -> buscarPedido();
                case 4 -> relatorio();
                case 0 -> System.out.println("Encerrando...");
                default -> System.out.println("Opção inválida");
            }

        } while (opcao != 0);
    }

    private void menu() {

        System.out.println("\n=== SISTEMA SOLID ===");
        System.out.println("1 - Novo Pedido");
        System.out.println("2 - Listar Pedidos");
        System.out.println("3 - Buscar Pedido");
        System.out.println("4 - Relatorio");
        System.out.println("0 - Sair");
        System.out.print("Escolha: ");
    }

    private void novoPedido() {

        scanner.nextLine();

        System.out.print("Nome do cliente: ");
        String nome = scanner.nextLine();

        System.out.print("Tipo (1-COMUM, 2-PREMIUM, 3-VIP): ");
        int tipo = scanner.nextInt();

        TipoCliente tipoCliente = switch (tipo) {
            case 2 -> TipoCliente.PREMIUM;
            case 3 -> TipoCliente.VIP;
            default -> TipoCliente.COMUM;
        };

        Cliente cliente = new Cliente(nome, tipoCliente);

        Pedido pedido = pedidoService.criarPedido(cliente);

        System.out.print("Quantidade de itens: ");
        int qtd = scanner.nextInt();

        for (int i = 0; i < qtd; i++) {

            scanner.nextLine();

            System.out.print("Nome do item: ");
            String nomeItem = scanner.nextLine();

            System.out.print("Preco: ");
            double preco = scanner.nextDouble();

            pedidoService.adicionarItem(pedido.getId(),
                    new Item(nomeItem, preco));
        }

        pedidoService.finalizarPedido(pedido.getId());

        System.out.println("Pedido criado!");
    }

    private void listarPedidos() {

        List<Pedido> pedidos = pedidoService.listarPedidos();

        pedidos.forEach(System.out::println);
    }

    private void buscarPedido() {

        System.out.print("ID: ");
        int id = scanner.nextInt();

        Pedido pedido = pedidoService.buscarPedido(id);

        System.out.println(pedido);
    }

    private void relatorio() {

        List<Pedido> pedidos = pedidoService.listarPedidos();

        relatorioService.gerarRelatorio(pedidos);
    }
}