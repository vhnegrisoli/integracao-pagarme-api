package br.com.biot.integracaopagarmeapi.modulos.cartao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartaoResponse {

    private String id;

    private String usuarioId;

    private String bandeira;

    private String ultimosDigitos;

    private String pais;

    public static CartaoResponse converterDe(CartaoClientResponse clientResponse,
                                             String usuarioId) {
        var response = new CartaoResponse();
        BeanUtils.copyProperties(clientResponse, response);
        response.setUsuarioId(usuarioId);
        return response;
    }
}
