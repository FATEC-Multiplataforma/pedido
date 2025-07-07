package br.com.fatec.frete.repository;

import br.com.fatec.frete.entity.Frete;

public interface FreteRepository {
    Frete save(Frete frete);

    Frete findById(String id);

    void delete(String id);
}
