package br.com.fatec.frete.entity;

public record Endereco(
        String logradouro,
        String complemento,
        String bairro,
        String cep,
        String uf
) {
}