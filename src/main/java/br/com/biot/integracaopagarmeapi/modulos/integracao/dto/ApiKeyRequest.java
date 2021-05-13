package br.com.biot.integracaopagarmeapi.modulos.integracao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiKeyRequest {

    @JsonProperty("api_key")
    private String apiKey;

    public static ApiKeyRequest criar(String apiKey) {
        return new ApiKeyRequest(apiKey);
    }
}
