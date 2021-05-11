package br.com.biot.integracaopagarmeapi.modulos.cartao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartaoClientResponse {

    private String id;

    @JsonProperty("brand")
    private String bandeira;

    @JsonProperty("last_digits")
    private String ultimosDigitos;

    @JsonProperty("country")
    private String pais;
}
