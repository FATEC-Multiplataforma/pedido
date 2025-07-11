package br.com.fatec.frete.controller;

import br.com.fatec.frete.controller.adapter.FreteControllerAdapter;
import br.com.fatec.frete.controller.dto.response.FreteResponse;
import br.com.fatec.frete.controller.dto.response.FreteValorResponse;
import br.com.fatec.frete.repository.FreteRepository;
import br.com.fatec.frete.service.FreteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fretec/v1")
public class FreteController {
    private final FreteService service;
    private final FreteRepository repository;

    public FreteController(FreteService service, FreteRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/order/{id}")
    public FreteResponse getById(@PathVariable("id") String id) {
        return FreteControllerAdapter.cast(repository.findById(id));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/order/value/{uf}")
    public FreteValorResponse getValue(@PathVariable("uf") String uf) {
        return FreteControllerAdapter.cast(service.getValor(uf.toUpperCase()));
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/order/{id}")
    public void delete(@PathVariable("id") String id) {
        repository.delete(id);
    }

}
