package br.com.biot.integracaopagarmeapi.modulos.cliente.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoRequest {

    private String pais;
    private String estado;
    private String cidade;
    private String rua;
    private String numeroRua;
    private String cep;
    private String bairro;
    private String complemento;
}
