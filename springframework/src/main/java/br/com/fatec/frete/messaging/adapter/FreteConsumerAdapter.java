package br.com.fatec.frete.messaging.adapter;

import br.com.fatec.frete.entity.Endereco;
import br.com.fatec.frete.entity.Frete;
import br.com.fatec.frete.entity.enumerable.Status;
import br.com.fatec.frete.messaging.dto.PedidoFreteAmqp;

import java.math.BigDecimal;
import java.util.UUID;

public class FreteConsumerAdapter {
    private FreteConsumerAdapter() {
    }

    public static Frete cast(PedidoFreteAmqp pedido) {
        return new Frete(
                UUID.randomUUID().toString(),
                pedido.getClientId(),
                BigDecimal.ZERO,
                Status.PROCESSANDO,
                new Endereco(
                        pedido.getLogradouro(),
                        pedido.getComplemento(),
                        pedido.getBairro(),
                        pedido.getCep(),
                        pedido.getUf()));
    }
}
