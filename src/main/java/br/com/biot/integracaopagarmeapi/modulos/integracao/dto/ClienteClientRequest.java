package br.com.biot.integracaopagarmeapi.modulos.integracao.dto;

import br.com.biot.integracaopagarmeapi.modulos.cliente.dto.ClienteRequest;
import br.com.biot.integracaopagarmeapi.modulos.transacao.dto.ClienteDocumentoDto;
import br.com.biot.integracaopagarmeapi.modulos.util.JsonUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteClientRequest {

    private static final String TIPO_INDIVIDUO = "individual";

    @JsonProperty("api_key")
    private String apiKey;

    @JsonProperty("external_id")
    private String idExterno;

    @JsonProperty("name")
    private String nome;

    @JsonProperty("type")
    private String tipo;

    @JsonProperty("country")
    private String pais;

    @JsonProperty("email")
    private String email;

    @JsonProperty("documents")
    private List<ClienteDocumentoDto> documentos;

    @JsonProperty("phone_numbers")
    private List<String> numerosTelefone;

    public static ClienteClientRequest converterDe(ClienteRequest clienteRequest) {
        return ClienteClientRequest
            .builder()
            .idExterno(clienteRequest.getUsuarioId())
            .nome(clienteRequest.getNome())
            .email(clienteRequest.getEmail())
            .pais(clienteRequest.getEndereco().getPais())
            .numerosTelefone(clienteRequest.getTelefones())
            .documentos(clienteRequest
                .getDocumentos()
                .stream()
                .map(ClienteDocumentoDto::converterDe)
                .collect(Collectors.toList()))
            .tipo(TIPO_INDIVIDUO)
            .build();
    }

    public String toJson() {
        return JsonUtil.converterJsonParaString(this);
    }
}
