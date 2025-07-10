package br.com.fatec.frete.entity;

import br.com.fatec.frete.entity.enumerable.Status;

import java.math.BigDecimal;

public record Frete(
        String id,
        String clientId,
        BigDecimal valor,
        Status status,
        Endereco endereco
) {
}