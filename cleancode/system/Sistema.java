package cleancode.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import cleancode.model.*;
import cleancode.service.*;

public class Sistema {

    private Scanner sc = new Scanner(System.in);
    private PedidoService pedidoService = new PedidoService();
    private RelatorioService relatorioService = new RelatorioService();

    public void run() {

        int op = -1;

        while (op != 0) {

            menu();
            op = Integer.parseInt(sc.nextLine());

            switch (op) {

                case 1 -> novoPedido();
                case 2 -> listar();
                case 3 -> buscar();
                case 4 -> relatorio();
                case 0 -> System.out.println("fim");

            }
        }
    }

    private void menu() {

        System.out.println("1 - Novo Pedido");
        System.out.println("2 - Listar");
        System.out.println("3 - Buscar");
        System.out.println("4 - Relatorio");
        System.out.println("0 - Sair");

    }

    private void novoPedido() {

        System.out.println("Nome cliente:");
        String nome = sc.nextLine();

        Cliente cliente =
                new Cliente(1, nome, TipoCliente.COMUM);

        List<Item> itens = new ArrayList<>();

        itens.add(new Item("Produto", 100, 2));

        Pedido pedido =
                pedidoService.criarPedido(cliente, itens);

        System.out.println("Pedido criado: " + pedido.getId());

    }

    private void listar() {

        pedidoService.listar()
                .forEach(p ->
                        System.out.println(p.getId() + " " + p.getTotal())
                );

    }

    private void buscar() {

        System.out.println("Id:");

        int id = Integer.parseInt(sc.nextLine());

        Pedido p = pedidoService.buscar(id);

        if (p != null)
            System.out.println(p.getCliente().getNome());
        else
            System.out.println("Nao encontrado");

    }

    private void relatorio() {

        relatorioService
                .gerar(pedidoService.listar());

    }
}