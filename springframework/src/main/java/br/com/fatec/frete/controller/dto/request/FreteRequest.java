package br.com.fatec.frete.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

@Validated
public record FreteRequest(
        @NotBlank(message = "O id do cliente não pode ser vazio")
        String clientId,
        @NotBlank(message = "O CEP não pode ser vazio")
        String cep,
        @NotBlank(message = "O logradouro não pode ser vazio")
        String logradouro,
        String complemento,
        @NotBlank(message = "O bairro não pode ser vazio")
        String bairro,
        @NotBlank(message = "A localidade não pode ser vazio")
        String localidade,
        @NotBlank(message = "O UF não pode ser vazio")
        String uf
) {
}
