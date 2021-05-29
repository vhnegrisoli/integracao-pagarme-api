package br.com.biot.integracaopagarmeapi.modulos.transacao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemTransacaoRequest {

    private String id;
    private String titulo;
    private BigDecimal precoUnitario;
    private Long quantidade;
}
