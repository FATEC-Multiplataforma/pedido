package br.com.fatec.frete.repository.orm;

import br.com.fatec.frete.entity.enumerable.Status;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(value = "frete")
public record FreteOrm(
        @Id
        String id,
        @Indexed
        String client,
        BigDecimal valor,
        EnderecoOrm endereco,
        @Indexed
        Status status
) {
}
