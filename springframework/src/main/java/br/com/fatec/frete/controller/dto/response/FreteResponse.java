package br.com.fatec.frete.controller.dto.response;

import br.com.fatec.frete.entity.enumerable.Status;

import java.math.BigDecimal;

public record FreteResponse(
        String id,
        String clientId,
        BigDecimal valor,
        Status status,
        String uf
) {
}
