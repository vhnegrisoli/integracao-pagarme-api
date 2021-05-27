package br.com.biot.integracaopagarmeapi.modulos.transacao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoCobrancaDto {

    @JsonProperty("country")
    private String pais;

    @JsonProperty("state")
    private String estado;

    @JsonProperty("city")
    private String cidade;

    @JsonProperty("neighborhood")
    private String bairro;

    @JsonProperty("street")
    private String rua;

    @JsonProperty("street_number")
    private String numeroRua;

    @JsonProperty("zipcode")
    private String cep;
}
