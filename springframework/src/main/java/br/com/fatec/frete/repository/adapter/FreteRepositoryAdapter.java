package br.com.fatec.frete.repository.adapter;

import br.com.fatec.frete.entity.Endereco;
import br.com.fatec.frete.entity.Frete;
import br.com.fatec.frete.repository.orm.EnderecoOrm;
import br.com.fatec.frete.repository.orm.FreteOrm;

public class FreteRepositoryAdapter {
    private FreteRepositoryAdapter() {
    }

    public static FreteOrm cast(Frete frete) {
        Endereco endereco = frete.endereco();
        return new FreteOrm(
                frete.id(),
                frete.clientId(),
                frete.valor(),
                new EnderecoOrm(
                        endereco.uf(),
                        endereco.logradouro(),
                        endereco.complemento(),
                        endereco.bairro(),
                        endereco.cep()),
                frete.status());
    }

    public static Frete cast(FreteOrm orm) {
        EnderecoOrm endereco = orm.endereco();
        return new Frete(
                orm.id(),
                orm.client(),
                orm.valor(),
                orm.status(),
                new Endereco(
                        endereco.logradouro(),
                        endereco.complemento(),
                        endereco.bairro(),
                        endereco.cep(),
                        endereco.uf()));
    }

}
