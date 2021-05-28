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
public class ClienteDocumentoDto {

    private static final String TIPO_CPF = "cpf";

    @JsonProperty("type")
    private String tipo;

    @JsonProperty("number")
    private String numeroDocumento;

    public static ClienteDocumentoDto converterDe(String documento) {
        return ClienteDocumentoDto
            .builder()
            .tipo(TIPO_CPF)
            .numeroDocumento(documento)
            .build();
    }
}
