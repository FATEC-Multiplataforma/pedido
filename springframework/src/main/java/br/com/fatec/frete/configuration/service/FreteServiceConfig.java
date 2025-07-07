package br.com.fatec.frete.configuration.service;

import br.com.fatec.frete.repository.FreteRepository;
import br.com.fatec.frete.service.FreteService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FreteServiceConfig {
    @Bean
    public FreteService freteService(FreteRepository repository) {
        return new FreteService(repository);
    }
}