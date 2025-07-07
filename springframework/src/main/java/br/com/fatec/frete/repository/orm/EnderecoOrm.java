package br.com.fatec.frete.repository.orm;

import org.springframework.data.mongodb.core.index.Indexed;

public record EnderecoOrm(
        @Indexed
        String uf,
        String logradouro,
        String complemento,
        String bairro,
        String cep
) {
}
