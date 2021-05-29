package br.com.biot.integracaopagarmeapi.modulos.integracao.dto.transacao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static br.com.biot.integracaopagarmeapi.modulos.util.Constantes.TIPO_CPF;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDocumentoClientRequest {

    @JsonProperty("type")
    private String tipo;

    @JsonProperty("number")
    private String numeroDocumento;

    public static ClienteDocumentoClientRequest converterDe(String documento) {
        return ClienteDocumentoClientRequest
            .builder()
            .tipo(TIPO_CPF)
            .numeroDocumento(documento)
            .build();
    }
}
