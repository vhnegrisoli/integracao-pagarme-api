package br.com.biot.integracaopagarmeapi.modulos.integracao.dto.transacao;

import br.com.biot.integracaopagarmeapi.modulos.transacao.dto.ItemTransacaoRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemTransacaoClientRequest {

    private String id;

    @JsonProperty("title")
    private String titulo;

    @JsonProperty("unit_price")
    private BigDecimal precoUnitario;

    @JsonProperty("quantity")
    private Long quantidade;

    @JsonProperty("tangible")
    private Boolean tangivel;

    public static ItemTransacaoClientRequest converterDe(ItemTransacaoRequest itemTransacaoRequest) {
        var itemTransacaoClientRequest = new ItemTransacaoClientRequest();
        BeanUtils.copyProperties(itemTransacaoRequest, itemTransacaoClientRequest);
        itemTransacaoClientRequest.setTangivel(false);
        return itemTransacaoClientRequest;
    }
}
