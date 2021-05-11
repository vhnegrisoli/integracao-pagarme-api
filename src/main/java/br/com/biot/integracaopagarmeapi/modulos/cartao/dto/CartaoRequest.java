package br.com.biot.integracaopagarmeapi.modulos.cartao.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartaoRequest {

    private String dataExpiracaoCartao;

    private String numeroCartao;

    private String cvvCartao;

    private String nomeProprietarioCartao;
}
