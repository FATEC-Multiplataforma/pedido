package br.com.fatec.frete.service;

import br.com.fatec.frete.entity.Frete;
import br.com.fatec.frete.entity.enumerable.Status;
import br.com.fatec.frete.exception.NotFoundException;
import br.com.fatec.frete.repository.FreteRepository;

import java.math.BigDecimal;

public class FreteService {
    private final FreteRepository repository;

    public FreteService(FreteRepository repository) {
        this.repository = repository;
    }

    public Frete saveStatus(Frete frete, Status status) {
        return repository.save(new Frete(
                frete.id(),
                frete.clientId(),
                getValor(frete.endereco().uf()),
                status,
                frete.endereco()));
    }

    public BigDecimal getValor(final String uf) {
        return switch (uf) {
            case "SP", "PR" -> BigDecimal.ZERO;
            case "RJ", "SC", "RS" -> BigDecimal.valueOf(20);
            case "MG", "MT", "MS", "ES" -> BigDecimal.valueOf(50);
            default -> throw new NotFoundException("Frete não realizado");
        };
    }
}