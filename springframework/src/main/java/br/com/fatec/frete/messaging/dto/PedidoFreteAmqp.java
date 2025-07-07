package br.com.fatec.frete.messaging.dto;

public record PedidoFreteAmqp(
        String clientId,
        String cep,
        String logradouro,
        String complemento,
        String bairro,
        String localidade,
        String uf
) {
}
