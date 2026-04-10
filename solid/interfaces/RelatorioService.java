package solid.interfaces;

import solid.model.Pedido;
import java.util.List;

public interface RelatorioService {

    void gerarRelatorio(List<Pedido> pedidos);
}