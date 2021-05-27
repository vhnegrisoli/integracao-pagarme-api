package br.com.biot.integracaopagarmeapi.modulos.transacao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CobrancaDto {

    @JsonProperty("name")
    private String nome;

    @JsonProperty("address")
    private EnderecoCobrancaDto endereco = new EnderecoCobrancaDto();
}
