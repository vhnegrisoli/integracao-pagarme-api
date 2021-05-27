package br.com.biot.integracaopagarmeapi.modulos.transacao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemTransacaoDto {

    private String id;

    @JsonProperty("title")
    private String titulo;

    @JsonProperty("unit_price")
    private BigDecimal precoUnitario;

    @JsonProperty("quantity")
    private Long quantidade;

    @JsonProperty("tangible")
    private Boolean tangivel = Boolean.FALSE;
}
