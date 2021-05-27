package br.com.biot.integracaopagarmeapi.modulos.transacao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransacaoDto {

    @JsonProperty("api_key")
    private String apiKey;

    @JsonProperty("amount")
    private BigDecimal total;

    @JsonProperty("card_id")
    private String cartaoId;

    @JsonProperty("customer")
    private ClienteDto cliente;

    @JsonProperty("billing")
    private CobrancaDto cobranca;

    @JsonProperty("items")
    private List<ItemTransacaoDto> itens = new ArrayList<>();
}
