package br.com.fatec.frete.controller.adapter;

import br.com.fatec.frete.controller.dto.request.FreteRequest;
import br.com.fatec.frete.controller.dto.response.FreteResponse;
import br.com.fatec.frete.controller.dto.response.FreteValorResponse;
import br.com.fatec.frete.entity.Endereco;
import br.com.fatec.frete.entity.Frete;
import br.com.fatec.frete.entity.enumerable.Status;

import java.math.BigDecimal;
import java.util.UUID;

public class FreteControllerAdapter {
    private FreteControllerAdapter() {
    }

    public static FreteResponse cast(Frete frete) {
        return new FreteResponse(
                frete.id(),
                frete.client(),
                frete.valor(),
                frete.status(),
                frete.endereco().uf());
    }

    public static Frete cast(FreteRequest request) {
        return new Frete(
                UUID.randomUUID().toString(),
                request.clientId(),
                BigDecimal.ZERO,
                Status.PROCESSANDO,
                new Endereco(
                        request.logradouro(),
                        request.complemento(),
                        request.bairro(),
                        request.cep(),
                        request.uf().toUpperCase()));
    }

    public static FreteValorResponse cast(BigDecimal valor) {
        return new FreteValorResponse(valor);
    }
}
