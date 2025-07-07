package br.com.fatec.frete.service;

import br.com.fatec.frete.entity.Frete;
import br.com.fatec.frete.exception.NotFoundException;
import br.com.fatec.frete.repository.FreteRepository;

import java.math.BigDecimal;

public class FreteService {
    private final FreteRepository repository;

    public FreteService(FreteRepository repository) {
        this.repository = repository;
    }

    public Frete getFrete(Frete frete) {
        return repository.save(new Frete(
                frete.id(),
                frete.client(),
                getValor(frete.endereco().uf()),
                frete.status(),
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