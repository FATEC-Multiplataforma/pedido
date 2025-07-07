package br.com.fatec.frete.controller.dto.response;

import java.math.BigDecimal;

public record FreteValorResponse(
        BigDecimal valor
) {
}
