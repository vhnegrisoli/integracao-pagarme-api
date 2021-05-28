package br.com.biot.integracaopagarmeapi.modulos.cliente.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteRequest {

    private String usuarioId;
    private String nome;
    private String email;
    private List<String> telefones;
    private List<String> documentos;
    private EnderecoRequest endereco;
}